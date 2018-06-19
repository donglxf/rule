package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 规则属性配置表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_property_rel")
@Data
public class PropertyRel extends PropertyInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("rule_pro_rel_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long ruleProRelId;
    /**
     * 规则
     */
	@TableField("rule_id")
	@ApiModelProperty(required= true,value = "规则")
	private Long ruleId;
    /**
     * 规则属性
     */
	@TableField("rule_property_id")
	@ApiModelProperty(required= true,value = "规则属性")
	private Long rulePropertyId;
    /**
     * 规则属性值
     */
	@TableField("rule_property_value")
	@ApiModelProperty(required= true,value = "规则属性值")
	private String rulePropertyValue;



	@Override
	protected Serializable pkVal() {
		return this.ruleProRelId;
	}

	@Override
	public String toString() {
		return "PropertyRel{" +
			"ruleProRelId=" + ruleProRelId +
			", ruleId=" + ruleId +
			", rulePropertyId=" + rulePropertyId +
			", rulePropertyValue=" + rulePropertyValue +
			"}";
	}
}
