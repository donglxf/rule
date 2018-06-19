package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ActionRuleRel;
import com.ht.rule.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动作与规则信息关系表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionRuleRelService extends BaseService<ActionRuleRel> {

    /**
     * 描述：获取动作中间表和动作信息及 动作值信息
     *
     * @param * @param null
     * @return a
     * @autor 张鹏
     * @date 2017/12/25 17:23
     */
    List<ActionRuleRel> findActionVals(Long ruleId,Map<String,Long > data);

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则与动作关系集合信息
     * @param baseRuleActionRuleRelInfo 参数
     * @param page 分页参数
     */
    PageInfo<ActionRuleRel> findBaseRuleActionRuleRelInfoPage(ActionRuleRel baseRuleActionRuleRelInfo, PageInfo page) throws Exception;
}
