package com.ht.rule.common.api.entity.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * 测试枚举
 */
public enum EntityTypeEnum implements IEnum {
    /**
     * 整形
     */
    ORDER("O", "订单"),
    /**
     * 布尔类型
     */
    USER("U", "用户"),
    ;

    private String value;
    private String desc;

    EntityTypeEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    public String getCode(){return this.value;}
    @JsonValue
    public String getDesc(){
        return this.desc;
    }
}
