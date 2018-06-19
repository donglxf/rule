package com.ht.rule.common.comenum;

public enum RuleTestStateEnum {
	pass("1", "通过"), nopass("0", "未通过");

	private String code;
	private String name;

	RuleTestStateEnum(String code, String name) {
		this.code = code;
		this.name = name;
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

	public static RuleTestStateEnum findByName(String key) {
		RuleTestStateEnum[] list = RuleTestStateEnum.values();
		for (RuleTestStateEnum actionEnum : list) {
			if (key.equals(actionEnum.getName())) {
				return actionEnum;
			}
		}
		return null;
	}
}
