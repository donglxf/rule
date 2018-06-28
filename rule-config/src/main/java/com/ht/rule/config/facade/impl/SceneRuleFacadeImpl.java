package com.ht.rule.config.facade.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.util.RuleUtils;
import com.ht.rule.config.facade.SceneRuleFacade;
import com.ht.rule.common.api.vo.RuleItemTable;
import com.ht.rule.config.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 统一验证 标识的唯一性 服务组合件
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class SceneRuleFacadeImpl implements SceneRuleFacade {
    @Autowired
    private RuleInfoService infoService;

    @Autowired
    private SceneInfoService sceneInfoService;


    @Autowired
    private EntityInfoService entityInfoService;


    @Autowired
    private ConditionInfoService conditionInfoService;


    @Autowired
    private EntityItemInfoService entityItemInfoService;

    @Autowired
    private ActionRuleRelService actionRuleRelService;


    @Override
    @Cacheable(value = "risk-rule",key = "'getRules:'+#sceneId")
    public Map<String, Object> getScene4Grade(Long sceneId) throws Exception{
        Map<String ,Object > result = new HashMap<>();
        //动作次数
        int actionCounts = 0;
        int itemHeads = 0;
        int hasWeight  = 0;
        SceneInfo sceneInfo = sceneInfoService.selectById(sceneId);
        //获取该场景的规则
        Wrapper<RuleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("scene_id",sceneId);

        //获取键值对信息
        Map<String,Long> keyIdMap = entityInfoService.getEntityItemIdCode(sceneInfo.getBusinessId());


        List<RuleInfo> list = infoService.findListBySceneId(sceneId);
        if(list == null || list.size() < 1){
            return null;
        }
        //获取实体类集合
      //  List<EntityInfo> entityInfos = sceneEntityRelService.findBaseRuleEntityListByScene(sceneInfo);
        //获取变量集合
        //  List<RuleItemTable> itemTables =  sceneItemRelService.findItemTables(sceneId);
        int i = 0;
        //规则实际赋值
        for (RuleInfo rule : list){
            //是否有权值
            if(rule.getGroup() != null ){
                if(rule.getGroup().getWeight() == 1){
                    hasWeight = 1 + hasWeight;
                }
            }else{
                RuleGroup group = new RuleGroup();
                group.setIndex(i);
                group.setName("未分组");
                rule.setGroup(group);
            }
            i++;
            //条件
            List<ConditionInfo> conts = conditionInfoService.findRuleConditionInfoByRuleId(rule.getRuleId()) ;
            //转化
            for (ConditionInfo conditionInfo : conts){
                String itemId = RuleUtils.getConditionParamBetweenChar(conditionInfo.getConditionExpression()).get(0);
                //使用过的所有变量ids
                String itemIds = RuleUtils.getValBetweenChar(conditionInfo.getConditionExpression(),keyIdMap);
                //获取变量
                RuleItemTable itemTable = entityItemInfoService.findRuleItemTableById(Long.parseLong(itemId));
                conditionInfo.setItemTable(itemTable);
                conditionInfo.setValText(conditionInfo.getVal());
                //设置运算符
                String val = RuleUtils.getConditionOfVariable(conditionInfo.getConditionExpression());
                conditionInfo.setYsf(RuleUtils.getCondition(conditionInfo.getConditionExpression(),val));
                //转换去#符合
                if(val.indexOf("#") >= 0){
                    conditionInfo.setClazz("actionEntity");
                    val = val.replace('#',' ').trim();
                }
                if(val.indexOf("@") >= 0){
                    conditionInfo.setClazz("conditionSZ");
                    val = val.replace('@',' ').trim();
                }

                //设置使用的itemId
                conditionInfo.setItemIds(itemIds);
                //设置中文名 运算符
                conditionInfo.setYsfText(RuleUtils.getCondition(conditionInfo.getConditionDesc(),conditionInfo.getValText()));
                val = val.replaceAll("'","");
                conditionInfo.setVal(val);
                conditionInfo.setValText(conditionInfo.getValText().replaceAll("'",""));
            }
            // 动作集合
            //   List<ActionInfo> actionInfos = actionInfoService.findRuleActionListByRule(rule.getRuleId());
            //获取 动作中间表和相关的动作信息及参数信息，及值
            List<ActionRuleRel> rels = actionRuleRelService.findActionVals(rule.getRuleId(),keyIdMap);
            //TODO:评分卡 去掉权值
            rels.forEach(rel->{
                List<ActionParamValueInfo> paramValueInfos =  rel.getActionInfo().getParamValueInfoList();
                for (Iterator<ActionParamValueInfo> iter = paramValueInfos.iterator(); iter.hasNext();) {
                    ActionParamValueInfo pvIter = iter.next();
                    if(pvIter.getActionParamId() == 6L ){
                        iter.remove();
                    }
                }
            });
            actionCounts = rels.size();
            rule.setCons(conts);
            rule.setActionRels(rels);
            itemHeads = conts.size();
        }
        // result.put("entityInfos",entityInfos);
        result.put("itemHeads",itemHeads);
        result.put("rules",list);
        result.put("hasWeight",hasWeight == list.size() ? 0:1);
        //动作个数
        result.put("actionCounts",actionCounts);
        return result;
    }
}
