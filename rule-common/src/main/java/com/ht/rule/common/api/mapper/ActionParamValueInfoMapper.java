package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.ActionParamValueInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 动作参数对应的属性值信息表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionParamValueInfoMapper extends SuperMapper<ActionParamValueInfo> {
    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     *
     * @param baseRuleActionParamValueInfo 参数
     */
    List<ActionParamValueInfo> findBaseRuleActionParamValueInfoList(ActionParamValueInfo baseRuleActionParamValueInfo);


    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    //ActionParamValueInfo findRuleParamValueByActionParamId(@Param("paramId") Long paramId);

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    ActionParamValueInfo findRuleParamValueByActionParamId(@Param("paramId") Long paramId, @Param("ruleActionRelId") Long ruleActionRelId);

}
