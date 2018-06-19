package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageInfo;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.mapper.*;
import com.ht.rule.common.util.StringUtil;
import com.ht.rule.config.model.BaseRulePropertyRelInfo;
import com.ht.rule.config.service.RuleInfoService;
import com.ht.ussp.bean.LoginUserInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则信息 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class RuleInfoServiceImpl extends BaseServiceImpl<RuleInfoMapper, RuleInfo> implements RuleInfoService {
    @Autowired
    LoginUserInfoHelper userInfoHelper;
    @Resource
    private RuleInfoMapper infoMapper;

    @Resource
    private ConditionInfoMapper conditionInfoMapper;

    @Resource
    private ActionRuleRelMapper actionRuleRelMapper;
    @Resource
    private ActionParamValueInfoMapper actionParamValueInfoMapper;
    @Resource
    private SceneItemRelMapper sceneItemRelMapper;
    @Resource
    private SceneEntityRelMapper sceneEntityRelMapper;
    @Resource
    private RuleGroupMapper groupMapper;


    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 查询规则属性信息
     *
     * @param propertyInfo 参数
     */
    @Override
    public List<PropertyInfo> findBaseRulePropertyInfoList(PropertyInfo propertyInfo) throws Exception {
        return this.infoMapper.findBaseRulePropertyInfoList(propertyInfo);
    }

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则获取已经配置的属性信息
     *
     * @param ruleId 参数
     */
    @Override
    public List<PropertyRel> findRulePropertyListByRuleId(final Long ruleId) throws Exception {
        return this.infoMapper.findRulePropertyListByRuleId(ruleId);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取对应的规则规则信息
     *
     * @param sceneInfo 参数
     */
    @Override
    public List<RuleInfo> findBaseRuleListByScene(SceneInfo sceneInfo) throws Exception {
        if (null == sceneInfo || (null == sceneInfo.getSceneId() &&
                StringUtil.strIsNull(sceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }

        return this.infoMapper.findBaseRuleListByScene(sceneInfo);
    }



    @Override
    public void clearBySceneId(Long sceneId ) {
        String ruleIds = "";
        //删除规则
        Wrapper<RuleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("scene_id",sceneId);
        //查询所有规则
        List<RuleInfo> rules = infoMapper.selectList(wrapper);
        for (RuleInfo rule : rules){
            //查询所有条件,且删除
            Wrapper<ConditionInfo> conwrapper = new EntityWrapper<>();
            conwrapper.eq("rule_id",rule.getRuleId());
            conditionInfoMapper.delete(conwrapper);

            //查询 规则动作表
            Wrapper<ActionRuleRel> actionRuleRelWrapper = new EntityWrapper<>();
            actionRuleRelWrapper.eq("rule_id",rule.getRuleId());
            List<ActionRuleRel> actionRuleRels = actionRuleRelMapper.selectList(actionRuleRelWrapper);
            for (ActionRuleRel rel : actionRuleRels){
                //删除所有相关的值得表
                Wrapper<ActionParamValueInfo> actionParamValueInfoWrapper = new EntityWrapper<>();
                actionParamValueInfoWrapper.eq("rule_action_rel_id",rel.getRuleActionRelId());
                actionParamValueInfoMapper.delete(actionParamValueInfoWrapper);
            }
            //删除 规则动作表
            actionRuleRelMapper.delete(actionRuleRelWrapper);

        }
        //删除规则
        this.infoMapper.delete(wrapper);

        //删 场景 变量表
        Wrapper<SceneItemRel> sceneItemRelWrapper = new EntityWrapper<>();
        sceneItemRelWrapper.eq("scene_id",sceneId);
        sceneItemRelMapper.delete(sceneItemRelWrapper);
        //删 场景 实体类表
        Wrapper<SceneEntityRel> sceneEntityRelWrapper = new EntityWrapper<>();
        sceneEntityRelWrapper.eq("scene_id",sceneId);
        sceneEntityRelMapper.delete(sceneEntityRelWrapper);
        //删除 场景 规则分组表
        Wrapper<RuleGroup> groupWrapper = new EntityWrapper<>();
        groupWrapper.eq("scene_id",sceneId);
        groupMapper.delete(groupWrapper);
    }

    @Override
    public RuleInfo addRule(SceneInfo scene, int index) {
        long creUid = 111;
        //添加规则
        RuleInfo rule = new RuleInfo();
        rule.setCreTime(new Date());
        rule.setCreUserId(StringUtils.isEmpty( userInfoHelper.getUserId()) ? "-1" :  userInfoHelper.getUserId());
        rule.setIsEffect(1);
        rule.setRuleName(scene.getSceneIdentify()+":"+index);
        rule.setRuleDesc("规则-"+index);
        rule.setRuleEnabled(1);
        rule.setSceneId(scene.getSceneId());
        this.infoMapper.insert(rule);
        return  rule;
    }

    @Override
    public List<RuleInfo> findListBySceneId(Long sceneId) {
        return this.infoMapper.findListBySceneId(sceneId);
    }



    @Override
    public PageInfo<RuleInfo> findBaseRuleInfoPage(RuleInfo baseRuleInfo, PageInfo page) throws Exception {
        List<RuleInfo> list = this.infoMapper.findBaseRuleInfoList(baseRuleInfo);
        return new PageInfo<>(list);
    }

}
