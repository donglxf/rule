package com.ht.rule.common.api.mapper;

import com.ht.rule.common.api.entity.ConditionInfo;
import com.ht.rule.common.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 规则条件信息表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ConditionInfoMapper extends SuperMapper<ConditionInfo> {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取规则条件信息
     * @param baseRuleConditionInfo 参数
     */
    List<ConditionInfo> findBaseRuleConditionInfoList(ConditionInfo baseRuleConditionInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取对应的条件信息
     * @param ruleId 规则id
     */
    List<ConditionInfo> findRuleConditionInfoByRuleId(@Param("ruleId") Long ruleId, @Param("relFlag") Integer relFlag);

}
