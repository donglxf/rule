package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class DroolsParamter4Batch {
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
	 * 版本号
	 */
	@ApiModelProperty(required= true,value = "业务线编号")
	private String businessCode;
	/**
	 * 调用类型
	 * 1：业务调用；2：规则验证;
	 */
	@ApiModelProperty(required= true,value = "调用类型")
	private String type ="1";
	/**
	 * 业务数据
	 */
	@ApiModelProperty(required= true,value = "业务数据")
	private List<Map<String, Object>> datas;

}
