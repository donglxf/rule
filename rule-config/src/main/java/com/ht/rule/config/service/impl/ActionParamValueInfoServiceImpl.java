package com.ht.rule.config.service.impl;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ActionInfo;
import com.ht.rule.common.api.entity.ActionParamInfo;
import com.ht.rule.common.api.entity.ActionParamValueInfo;
import com.ht.rule.common.api.entity.ActionRuleRel;
import com.ht.rule.common.api.mapper.ActionInfoMapper;
import com.ht.rule.common.api.mapper.ActionParamInfoMapper;
import com.ht.rule.common.api.mapper.ActionParamValueInfoMapper;
import com.ht.rule.common.api.mapper.ActionRuleRelMapper;
import com.ht.rule.config.service.ActionParamValueInfoService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.ussp.bean.LoginUserInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 动作参数对应的属性值信息表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class ActionParamValueInfoServiceImpl extends BaseServiceImpl<ActionParamValueInfoMapper, ActionParamValueInfo> implements ActionParamValueInfoService {
    @Autowired
    LoginUserInfoHelper userInfoHelper;
    @Resource
    private ActionParamValueInfoMapper actionParamValueInfoMapper;

    @Resource
    private ActionRuleRelMapper actionRuleRelMapper;

    @Resource
    private ActionParamInfoMapper actionParamInfoMapper;

    @Resource
    private ActionInfoMapper actionInfoMapper;


    @Override
    public ActionParamValueInfo findRuleParamValueByActionParamId(Long paramId) throws Exception {
        return null;
    }

 /*   @Override
    public void add(ActionParamValueInfo actionValue, Long ruleId) {
        //获取参数
        ActionParamInfo paramInfo = actionParamInfoMapper.selectById(actionValue.getActionParamId());
        //添加动作规则中间表,确定是否需要新增一个 动作规则中间表
        ActionRuleRel relWhere = new ActionRuleRel();
        relWhere.setRuleId(ruleId);
        relWhere.setActionId(paramInfo.getActionId());
        ActionRuleRel rel = actionRuleRelMapper.selectOne(relWhere);
        if(rel == null ){
            rel = new ActionRuleRel();
            rel.setRuleId(ruleId);
            rel.setActionId(paramInfo.getActionId());
            rel.setCreTime(new Date());
            rel.setIsEffect(1);
            rel.setCreUserId( StringUtils.isEmpty( userInfoHelper.getUserId()) ? "-1" :  userInfoHelper.getUserId());
            actionRuleRelMapper.insert(rel);
        }
        //方法名
        ActionInfo actionInfo = actionInfoMapper.selectById(paramInfo.getActionId());
        //添加值得表
        actionValue.setCreTime(new Date());
        actionValue.setCreUserId(StringUtils.isEmpty( userInfoHelper.getUserId()) ? "-1" :  userInfoHelper.getUserId());
        actionValue.setIsEffect(1);
        actionValue.setRuleActionRelId(rel.getRuleActionRelId());
        actionValue.setRemark("["+actionInfo.getActionName()+"]:"+actionValue.getParamValue()+"("+paramInfo.getActionParamName()+")");
        actionParamValueInfoMapper.insert(actionValue);
    }*/

    @Override
    public String addAndRemark(List<ActionParamValueInfo> actionValues, Long ruleId) {
        ActionInfo actionInfo = null;
        String actionRemark = "";
        boolean flag = true;
        ActionRuleRel rel = new ActionRuleRel();
        for (ActionParamValueInfo actionValue : actionValues) {
            //获取参数
            ActionParamInfo paramInfo = actionParamInfoMapper.selectById(actionValue.getActionParamId());
            if (actionInfo == null) {
                actionInfo = actionInfoMapper.selectById(paramInfo.getActionId());
            }
            if (flag) {
                //添加中间表
                rel.setRuleId(ruleId);
                rel.setActionId(paramInfo.getActionId());
                rel.setCreTime(new Date());
                rel.setIsEffect(1);
                rel.setCreUserId(StringUtils.isEmpty(userInfoHelper.getUserId()) ? "-1" : userInfoHelper.getUserId());
                actionRuleRelMapper.insert(rel);
                flag = false;
            }
            //添加值得表
            actionValue.setCreTime(new Date());
            actionValue.setCreUserId(StringUtils.isEmpty(userInfoHelper.getUserId()) ? "-1" : userInfoHelper.getUserId());
            actionValue.setIsEffect(1);
            actionValue.setRuleActionRelId(rel.getRuleActionRelId());
            actionValue.setRemark("[" + actionInfo.getActionName() + "]:" + actionValue.getParamValue() + "(" + paramInfo.getActionParamName() + ")");
            actionParamValueInfoMapper.insert(actionValue);

            if (StringUtils.isBlank(actionRemark)) {
                actionRemark = "那么=>" + actionValue.getRemark();
            } else {
                String paramVal = "," + actionValue.getRemark().substring(actionValue.getRemark().indexOf(":") + 1);
                actionRemark += paramVal;
            }

        }
        return actionRemark;
    }

    @Override
    public List<ActionParamValueInfo> findActionParamValByRuleId(Long ruleId) {
        return null;
    }


    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     *
     * @param baseRuleActionParamValueInfo 参数
     * @param page                         分页参数
     */
    @Override
    public PageInfo<ActionParamValueInfo> findBaseRuleActionParamValueInfoPage(ActionParamValueInfo baseRuleActionParamValueInfo, PageInfo page) throws Exception {
        List<ActionParamValueInfo> list = this.actionParamValueInfoMapper.findBaseRuleActionParamValueInfoList(baseRuleActionParamValueInfo);
        return new PageInfo<>(list);
    }

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    @Override
    public ActionParamValueInfo findRuleParamValueByActionParamId(Long paramId, Long ruleActionRelId) throws Exception {
        if (null == paramId && null == ruleActionRelId) {
            throw new NullPointerException("参数缺失");
        }
        return this.actionParamValueInfoMapper.findRuleParamValueByActionParamId(paramId, ruleActionRelId);
    }


}
