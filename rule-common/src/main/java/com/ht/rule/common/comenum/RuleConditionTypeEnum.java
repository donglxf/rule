package com.ht.rule.common.comenum;

public enum RuleConditionTypeEnum {

	general("1", "general", "普通"), contains("2", "contains", "包含"), memberof("3", "memberOf", "属于");

	private String type;
	private String code;
	private String name;

	RuleConditionTypeEnum(String type, String code, String name) {
		this.type = type;
		this.code = code;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
