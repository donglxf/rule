package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.SceneVersion;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 *
 * </p>
 *
 * @author dyb
 * @since 2018-1-27
 */
@ApiModel
public class SceneVersionInfoVo extends SceneVersion {

	private String name;
	private String value;

	private String senceType;
	private String businessName;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getSenceType() {

		return senceType;
	}

	public void setSenceType(String senceType) {
		this.senceType = senceType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
