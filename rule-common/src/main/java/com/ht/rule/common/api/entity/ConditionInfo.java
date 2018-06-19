package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ht.rule.common.api.vo.RuleItemTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 规则条件信息表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_condition_info")
@Data
public class ConditionInfo extends Model<ConditionInfo> {

    private static final long serialVersionUID = 1L;
	@TableField(exist = false)
    private RuleItemTable itemTable;

	@TableField(exist = false)
	private String itemIds;
	/**
     * 主键
     */
    @TableId("condition_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long conditionId;
    /**
     * 规则
     */
	@TableField("rule_id")
	@ApiModelProperty(required= true,value = "规则")
	private Long ruleId;
    /**
     * 条件名称
     */
	@TableField("condition_name")
	@ApiModelProperty(required= true,value = "条件名称")
	private String conditionName;
	/**
	 * 值
	 */
	@TableField("val")
	@ApiModelProperty(required= true,value = "值")
	@NotNull(message = "条件值不能为空")
	@Length(min = 1, max = 255, message = "条件值长度超长了")
	//@Pattern(regexp="^[^ `~!@#$%^&*()+=|{}''\\\\[\\\\]<>/~@#￥%……&*（）——+|{}]+$",message="条件值不能包括特殊字符")
	private String val;

	@TableField(exist = false)
	@ApiModelProperty(required= true,value = "中文描述值")
	private String valText;

	@TableField(exist = false)
	@ApiModelProperty(required= true,value = "运算符")
	private String ysfText;

	public String getYsfText() {
		return ysfText;
	}

	public void setYsfText(String ysfText) {
		this.ysfText = ysfText;
	}

	@TableField(exist = false)
	@ApiModelProperty(required= true,value = "运算符")
	private String ysf;
	//样式查询的时候用
	@TableField(exist = false)
	private String clazz;


	/**
     * 条件表达式
     */
	@TableField("condition_expression")
	@ApiModelProperty(required= true,value = "条件表达式")
	private String conditionExpression;
    /**
     * 条件描述
     */
	@TableField("condition_desc")
	@ApiModelProperty(required= true,value = "条件描述")
	private String conditionDesc;
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

	@Override
	protected Serializable pkVal() {
		return this.conditionId;
	}

	@Override
	public String toString() {
		return "ConditionInfo{" +
			"conditionId=" + conditionId +
			", ruleId=" + ruleId +
			", conditionName=" + conditionName +
			", conditionExpression=" + conditionExpression +
			", conditionDesc=" + conditionDesc +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
