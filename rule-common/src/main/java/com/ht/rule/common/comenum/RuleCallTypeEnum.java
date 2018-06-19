package com.ht.rule.common.comenum;

public enum RuleCallTypeEnum {

	business("1", "business", "业务调用"), model("3", "model", "模型调用"), rule("2", "rule", "规则调用"),modelValidation("4","modelValidation","模型验证调用");

	private String type;
	private String code;
	private String name; // 调用来源

	RuleCallTypeEnum(String type, String code, String name) {
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
