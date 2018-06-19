package com.ht.rule.common.comenum;

/**
 * 企业经营状态枚举
 *
 * @author dyb
 * @since 2018-02-27
 */
public enum EntstatusEnum {
    closeUp("1", "停业"), outOfBusiness("2", "歇业"), liquidate("3", "清算"), normal("4", "在营（开业）");

    private String value;
    private String name;

    EntstatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static EntstatusEnum findById(String value) {
        EntstatusEnum[] val = EntstatusEnum.values();
        for (EntstatusEnum v : val) {
            if (value.equals(v.getValue())) {
                return v;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
