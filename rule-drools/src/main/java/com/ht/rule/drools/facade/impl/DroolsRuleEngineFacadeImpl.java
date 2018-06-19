package com.ht.rule.drools.facade.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.mapper.SceneInfoVersionMapper;
import com.ht.rule.common.util.*;
import com.ht.rule.common.vo.model.drools.*;
import com.ht.rule.drools.facade.DroolsRuleEngineFacade;
import com.ht.rule.drools.service.*;
import com.ht.rule.drools.util.DroolsUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类 规则引擎统一服务
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
@Service
@Log4j2
public class DroolsRuleEngineFacadeImpl implements DroolsRuleEngineFacade {
    @Autowired
    private SceneInfoVersionMapper sceneInfoVersionMapper;
    @Autowired
    private RuleActionVersionService ruleActionVersionService;

    //换行符
    private static final String lineSeparator = System.getProperty("line.separator");
    //特殊处理的规则属性(字符串)
    private static final String[] arr = new String[]{"date-effective", "date-expires", "dialect", "activation-group", "agenda-group", "ruleflow-group"};

    private static Map<String,String> codeMap = null;

    @Override
    public RuleExecutionObject excute(RuleExecutionObject ruleExecutionObject, SceneInfoVersion scene) throws Exception {
        Long sceneId = scene.getSceneId();
        try {
            //获取ksession
            KieSession ksession = DroolsUtil.getInstance().getDrlSessionInCache(String.valueOf(sceneId));
            if (ksession != null) {
                //直接执行
                return executeRuleEngine(ksession, ruleExecutionObject,sceneId, scene.getVersionId());
            } else {
                //重新编译规则，然后执行
                return this.compileRuleAndexEcuteRuleEngine(scene.getRuleDrl(), ruleExecutionObject, sceneId, scene.getVersionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public SceneInfoVersion getScenInfoVersion(String scene, String version, Integer type) {
        Wrapper<SceneInfoVersion> wrapper = new EntityWrapper<>();
        wrapper.eq("s.scene_identify",scene);
        wrapper.eq("scene_type",type);
        if(StringUtils.isNotBlank(version)){
            wrapper.eq("v.`version`",version);
        }
        wrapper.orderBy("v.`version`",false);
        List<SceneInfoVersion> list = sceneInfoVersionMapper.selectList(wrapper);
        if(list.isEmpty()){
            return null;
        }
        return list.get(1);
    }

    @Override
    public RuleExecutionObject change2RuleExcutionObject(DroolsParamter paramter, SceneInfoVersion sceneInfoVersion) {

        log.info("规则入参：" + JSON.toJSONString(paramter));
        RuleExecutionObject object = new RuleExecutionObject();
        Map<String, Object> mapData = paramter.getData();
        object.addFactObject(mapData);
        RuleExecutionResult result = new RuleExecutionResult();
        object.setGlobal("_result", result);

        return object;
    }

    @Override
    public void log4drools(List<DroolsActionForm> actionForms, DroolsParamter paramter, SceneInfoVersion sceneInfoVersion) {

    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 规则执行方法
     *
     * @param session             会话
     * @param ruleExecutionObject 参数
     */
    private RuleExecutionObject executeRuleEngine(KieSession session, RuleExecutionObject ruleExecutionObject, final Long sceneId, final Long versionId) throws Exception {
        try {
            // 1.插入全局对象
            Map<String, Object> globalMap = ruleExecutionObject.getGlobalMap();
            for (Object glb : globalMap.entrySet()) {
                Map.Entry global = (Map.Entry) glb;
                String key = (String) global.getKey();
                Object value = global.getValue();
                System.out.println("插入Global对象:" + value.getClass().getName());
                session.setGlobal(key, value);
            }
            // 2.插入fact对象
            // 2.1插入业务fact对象
            List<Object> factObjectList = ruleExecutionObject.getFactObjectList();
            for (Object o : factObjectList) {
                System.out.println("插入Fact对象：" + o.getClass().getName());
                session.insert(o);
            }
            // 2.2将 ruleExecutionObject 也插入到规则中，回调使用
            session.insert(ruleExecutionObject);
            // 3.将规则涉及的所有动作实现类插入到规则中(如果没有，则不处理)
            List<RuleActionVersion> actionList = ruleActionVersionService.selectList(new EntityWrapper<RuleActionVersion>().eq("version_id", versionId));
            for (RuleActionVersion rule : actionList) {
                DroolsActionService actionService = SpringContextHolder.getBean(rule.getActionClazzIdentify());
                log.info("bean:>>>>>>>>" + rule.getActionClazzIdentify());
                session.insert(actionService);
            }

            //执行规则
            //1,是否全部执行
            if (ruleExecutionObject.isExecuteAll()) {
                int count = session.fireAllRules();
//                RuleExecutionResult boj= (RuleExecutionResult) ruleExecutionObject.getGlobalMap().get("_result");
//                boj.getMap().put("count",count);
                 log.info("命中规则条数：" + count);
            } else {
                //2,执行以 ruleExecutionObject里规则名开头的规则
                session.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleExecutionObject.getRuleName()));
            }
            return ruleExecutionObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            //释放资源
            session.dispose();
        }
    }



    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 编译规则脚本，并执行规则
     *
     * @param droolRuleStr        规则脚本
     * @param ruleExecutionObject 参数
     */
    private RuleExecutionObject compileRuleAndexEcuteRuleEngine(String droolRuleStr, RuleExecutionObject ruleExecutionObject, final Long sceneId, final Long versionId) throws Exception {
        //KieSession对象
        KieSession session;
        try {
            //编译规则脚本,返回KieSession对象
            session = DroolsUtil.getInstance().getDrlSession(droolRuleStr, sceneId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Drools初始化失败，请检查Drools语句！");
        }
        //执行规则
        return this.executeRuleEngine(session, ruleExecutionObject, sceneId, versionId);
    }

}
