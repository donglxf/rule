package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.ActionParamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 动作参数信息表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionParamInfoMapper extends SuperMapper<ActionParamInfo> {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取动作参数信息
     * @param baseRuleActionParamInfo 参数
     */
    List<ActionParamInfo> findBaseRuleActionParamInfoList(ActionParamInfo baseRuleActionParamInfo);

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据动作id获取动作参数信息
     * @param actionId 参数
     */
    List<ActionParamInfo> findRuleActionParamByActionId(@Param("actionId") Long actionId);

}
