package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class RuleExcuteResult {


    public RuleExcuteResult(){
        super();
    }

    public RuleExcuteResult(int code, String msg, List<DroolsActionForm> data, String senceVersoionId) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.senceVersoionId=senceVersoionId;
    }
    /**
     * 错误代码
     */
    @ApiModelProperty(required = true, value = "错误代码")
    private int code;  // 0-成功,其他失败
    /**
     * 错误描述
     */
    @ApiModelProperty(required = true, value = "错误描述")
    private String msg;

    /**
     * 传递给请求者的数据
     */
    @ApiModelProperty(value = "传递给请求者的数据")
    private List<DroolsActionForm> data;

    @ApiModelProperty(value = "评分卡-平分")
    private double grade;
    @ApiModelProperty(value = "决策版本")
    private String senceVersoionId;

}
