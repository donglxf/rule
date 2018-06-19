package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.SceneInfo;
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
public class SceneInfoVo extends SceneInfo {

	private String name;
	private String value;


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
