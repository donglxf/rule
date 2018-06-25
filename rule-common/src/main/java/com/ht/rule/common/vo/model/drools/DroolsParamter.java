package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel
public class DroolsParamter {
	/**
	 * 场景code
	 */
	@ApiModelProperty(required= true,value = "场景code",example = "HT")
	private String sence;
	/**
	 * 版本号
	 */
	@ApiModelProperty(required= true,value = "版本号",example = "1.6")
	private String version;
	/**
	 * 调用类型
	 * 1：业务调用；2：规则验证;
	 */
	@ApiModelProperty(required= true,value = "调用类型",example = "1")
	private String type ="1";
	@ApiModelProperty(required= true,value = "业务线code",example = "HT")
	private String businessCode;
	/**
	 * 业务数据
	 */
	@ApiModelProperty(required= true,value = "业务数据,是对象code+\"_\"+变量code",example = "{'dyr_dyrtype':'担保公司','dyr_edtype':'一般抵押'}")
	private Map<String, Object> data;

}
