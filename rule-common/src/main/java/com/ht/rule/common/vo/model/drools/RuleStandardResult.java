/**
 * dyb 20180206
 * 格式化规则执行返回结果
 */
package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@ApiModel
@Data
public class RuleStandardResult {
    @ApiModelProperty(value = "规则执行列表")
    private List<String> ruleList = new ArrayList<String>();
    @ApiModelProperty(required= true,value = "规则执行对应结果")
    private List<String> result = new ArrayList<>();
    @ApiModelProperty(required= true,value = "规则执行对应日志号")
    private List<String> logIdList = new ArrayList<>();
}
