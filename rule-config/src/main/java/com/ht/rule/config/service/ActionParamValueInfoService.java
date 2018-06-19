package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ActionParamValueInfo;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 动作参数对应的属性值信息表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionParamValueInfoService extends BaseService<ActionParamValueInfo> {


    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    ActionParamValueInfo findRuleParamValueByActionParamId(final Long paramId)throws Exception;

    /**
     * 描述：添加动作相关,并返回动作描述
     *
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2017/12/22 17:49
     */
    String addAndRemark(List<ActionParamValueInfo> actionValues, Long ruleId);

    /**
     * 描述：通过规则获取动作 参数和值，及动作名称
     *
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2017/12/25 15:56
     */
    List<ActionParamValueInfo> findActionParamValByRuleId(Long ruleId);

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     * @param baseRuleActionParamValueInfo 参数
     * @param page 分页参数
     */
    PageInfo<ActionParamValueInfo> findBaseRuleActionParamValueInfoPage(ActionParamValueInfo baseRuleActionParamValueInfo, PageInfo page) throws Exception;

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    ActionParamValueInfo findRuleParamValueByActionParamId(final Long paramId, final Long ruleActionRelId)throws Exception;
}
