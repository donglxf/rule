package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class DroolsForm {
	/**
	 * 场景code
	 */
	@ApiModelProperty(required= true,value = "场景code")
	private String sence;
	/**
	 * 版本号
	 */
	@ApiModelProperty(required= true,value = "版本号")
	private String version;
	/**
	 * 调用类型
	 * 1：业务调用；2：规则验证;
	 */
	@ApiModelProperty(required= true,value = "调用类型")
	private String type ="1";
	@ApiModelProperty(required= true,value = "业务线code")
	private String businessCode;
	/**
	 * 业务数据
	 */
	@ApiModelProperty(required= true,value = "业务数据")
	private List<ItemValForm> data;
	@ApiModel
	@Data
	public  class ItemValForm{
		@ApiModelProperty(required= true,value = "变量code")
		private String entityCode;
		@ApiModelProperty(required= true,value = "变量值")
		private Map<String,Object> data;
	}

}
