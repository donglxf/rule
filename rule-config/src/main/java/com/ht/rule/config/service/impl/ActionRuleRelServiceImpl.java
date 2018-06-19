package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ActionInfo;
import com.ht.rule.common.api.entity.ActionParamValueInfo;
import com.ht.rule.common.api.entity.ActionRuleRel;
import com.ht.rule.common.api.mapper.ActionParamValueInfoMapper;
import com.ht.rule.common.api.mapper.ActionRuleRelMapper;
import com.ht.rule.common.util.RuleUtils;
import com.ht.rule.config.service.ActionRuleRelService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动作与规则信息关系表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class ActionRuleRelServiceImpl extends BaseServiceImpl<ActionRuleRelMapper, ActionRuleRel> implements ActionRuleRelService {
    @Autowired
    private ActionRuleRelMapper actionRuleRelMapper;

    @Autowired
    private ActionParamValueInfoMapper actionParamValueInfoMapper;

    @Override
    public List<ActionRuleRel> findActionVals(Long ruleId,Map<String,Long > data) {
        //包含了 实体类
        List<ActionRuleRel> rels = actionRuleRelMapper.findActions(ruleId);
        //查询
        for (ActionRuleRel rel : rels){
            ActionInfo actionInfo = rel.getActionInfo();
            //获取属性值
            Wrapper<ActionParamValueInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("rule_action_rel_id",rel.getRuleActionRelId());
            List<ActionParamValueInfo> vals = actionParamValueInfoMapper.selectList(wrapper);
            vals.forEach(val ->{
                if( val.getParamValue().indexOf("#") >= 0){
                    val.setClazz("actionEntity");
                    val.getParamValue().replaceAll("#","");
                }else{
                    val.setClazz("actionVal");
                }

                if(StringUtils.isBlank(val.getParamText())){
                    val.setParamText(val.getParamValue());
                }
                val.setItemIds(RuleUtils.getValBetweenChar(val.getParamValue(),data));
            });
            actionInfo.setParamValueInfoList(vals);
        }
        return  rels;
    }
    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则与动作关系集合信息
     *
     * @param baseRuleActionRuleRelInfo 参数
     * @param page                      分页参数
     */
    @Override
    public PageInfo<ActionRuleRel> findBaseRuleActionRuleRelInfoPage(ActionRuleRel baseRuleActionRuleRelInfo, PageInfo page) throws Exception {

        List<ActionRuleRel> list = this.actionRuleRelMapper.findBaseRuleActionRuleRelInfoList(baseRuleActionRuleRelInfo);
        return new PageInfo<>(list);
    }
}
