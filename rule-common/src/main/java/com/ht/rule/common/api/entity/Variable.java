package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 规则引擎常用变量
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_variable")
public class Variable extends Model<Variable> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("variable_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long variableId;
    /**
     * 变量名称
     */
	@TableField("variable_name")
	@ApiModelProperty(required= true,value = "变量名称")
	private String variableName;
    /**
     * 变量类型（1条件2动作）
     */
	@TableField("variable_type")
	@ApiModelProperty(required= true,value = "变量类型（1条件2动作）")
	private Integer variableType;
    /**
     * 默认值
     */
	@TableField("default_value")
	@ApiModelProperty(required= true,value = "默认值")
	private String defaultValue;
    /**
     * 数值类型（ 1字符型 2数字型 3 日期型）
     */
	@TableField("value_type")
	@ApiModelProperty(required= true,value = "数值类型（ 1字符型 2数字型 3 日期型）")
	private Integer valueType;
    /**
     * 变量值
     */
	@TableField("variable_value")
	@ApiModelProperty(required= true,value = "变量值")
	private String variableValue;
    /**
     * 是否有效
     */
	@TableField("is_effect")
	@ApiModelProperty(required= true,value = "是否有效")
	private Integer isEffect;
    /**
     * 创建人
     */
	@TableField("cre_user_id")
	@ApiModelProperty(required= true,value = "创建人")
	private String creUserId;
    /**
     * 创建时间
     */
	@TableField("cre_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date creTime;
    /**
     * 备注
     */
	@ApiModelProperty(required= true,value = "备注")
	private String remark;


	public Long getVariableId() {
		return variableId;
	}

	public void setVariableId(Long variableId) {
		this.variableId = variableId;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Integer getVariableType() {
		return variableType;
	}

	public void setVariableType(Integer variableType) {
		this.variableType = variableType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.variableId;
	}

	@Override
	public String toString() {
		return "Variable{" +
			"variableId=" + variableId +
			", variableName=" + variableName +
			", variableType=" + variableType +
			", defaultValue=" + defaultValue +
			", valueType=" + valueType +
			", variableValue=" + variableValue +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
