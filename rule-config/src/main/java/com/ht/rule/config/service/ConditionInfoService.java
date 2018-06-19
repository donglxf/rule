package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ConditionInfo;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 规则条件信息表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ConditionInfoService extends BaseService<ConditionInfo> {


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     */
    List<ConditionInfo> findRuleConditionInfoByRuleId(final Long ruleId) throws Exception;

    ConditionInfo add(ConditionInfo conditionInfo, Long ruleId);


    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则获取规则条件信息
     *
     * @param baseRuleConditionInfo 参数
     * @param page                  分页参数
     */
    PageInfo<ConditionInfo> findBaseRuleConditionInfoPage(ConditionInfo baseRuleConditionInfo, PageInfo page) throws Exception;

}

