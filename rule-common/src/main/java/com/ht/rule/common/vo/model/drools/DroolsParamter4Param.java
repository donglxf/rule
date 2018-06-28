package com.ht.rule.common.vo.model.drools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class DroolsParamter4Param implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 场景code
	 */
	@ApiModelProperty(required= true,value = "场景code",example = "changeParam")
	private String sence;
	/**
	 * 版本号
	 */
	@ApiModelProperty(required= true,value = "版本号",example = "2.0")
	private String version;
	/**
	 * 版本号
	 */
	@ApiModelProperty(required= true,value = "业务线编号",example = "PARAM")
	private String businessCode;

	/**
	 * 业务数据
	 */
	@ApiModelProperty(required= true,value = "业务数据",example = " [{\"sex\":\"1\",\"status\":\"1\"}," +
			"  {\"sex\":\"2\",\"status\":\"2\"}," +
			"  {\"sex\":\"2\",\"status\":\"1\"}]")
	private List<Map<String, Object>> datas;

}
