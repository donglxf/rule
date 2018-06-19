package com.ht.rule.common.comenum;

public enum VbindStateEnum {
	bind("1", "已绑定"), nobind("0", "未绑定");

	private String code;
	private String name;

	VbindStateEnum(String code, String name) {
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

	public static VbindStateEnum findByName(String key) {
		VbindStateEnum[] list = VbindStateEnum.values();
		for (VbindStateEnum actionEnum : list) {
			if (key.equals(actionEnum.getName())) {
				return actionEnum;
			}
		}
		return null;
	}
}
