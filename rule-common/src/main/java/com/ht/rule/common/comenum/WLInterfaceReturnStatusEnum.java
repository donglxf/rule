package com.ht.rule.common.comenum;

/**
 * 外联接口返回状态枚举
 *
 * @author dyb
 * @since 2018-02-27
 */
public enum WLInterfaceReturnStatusEnum {
    success("0000", "正确"), paramErr("9997", "参数错误"), faild("9999", "执行失败"), exception("9998", "执行异常"), authenticationFaild("401", "鉴权失败");

    private String value;
    private String name;

    WLInterfaceReturnStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static WLInterfaceReturnStatusEnum findById(String value) {
        WLInterfaceReturnStatusEnum[] val = WLInterfaceReturnStatusEnum.values();
        for (WLInterfaceReturnStatusEnum v : val) {
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
