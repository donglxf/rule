package com.ht.rule.common.comenum;

public enum VerficationTypeEnum {
    auto(1, "自动"), manu(0, "手动");

    private int value;
    private String name;

    VerficationTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static VerficationTypeEnum findById(int value) {
        VerficationTypeEnum[] val = VerficationTypeEnum.values();
        for (VerficationTypeEnum v : val) {
            if (value == v.getValue()) {
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

    public int getValue() {

        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
