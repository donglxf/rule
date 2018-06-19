package com.ht.rule.common.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 动作参数信息表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@Data
public class ActionParamInfoVo  {

    private static final long serialVersionUID = 1L;
	@ApiModelProperty(required= true,value = "主键")
    private Long value;
	@ApiModelProperty(required= true,value = "描述")
	private  String text;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	/**
     * 主键
     */
	@ApiModelProperty(required= true,value = "主键")
	private Long actionParamId;
    /**
     * 动作id
     */
	@ApiModelProperty(required= true,value = "动作id")
	private Long actionId;
    /**
     * 参数名称
     */
	@ApiModelProperty(required= true,value = "参数名称")
	private String actionParamName;
    /**
     * 参数描述
     */
	@ApiModelProperty(required= true,value = "参数描述")
	private String actionParamDesc;
    /**
     * 标识
     */
	@ApiModelProperty(required= true,value = "标识")
	private String paramIdentify;

}
