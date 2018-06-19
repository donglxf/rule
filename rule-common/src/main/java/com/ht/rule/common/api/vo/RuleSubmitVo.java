package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.ActionParamValueInfo;
import com.ht.rule.common.api.entity.ConditionInfo;
import com.ht.rule.common.api.entity.RuleGroup;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
@ApiModel
@Data
public class RuleSubmitVo {
    /**
     * 分组和权值设置
     */
    private RuleGroup group;
    /**
     * 条件集合
     */
    @NotNull
    @Valid
    private List<ConditionInfo> conditionInfos;
    /**
     * 动作集合
     */
    @NotNull
    @Valid
    private List<List<ActionParamValueInfo>> actionInfos;

}
