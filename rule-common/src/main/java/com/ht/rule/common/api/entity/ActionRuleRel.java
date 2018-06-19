package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 动作与规则信息关系表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_action_rule_rel")
@Data
public class ActionRuleRel extends Model<ActionRuleRel> {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private ActionInfo actionInfo;
	/**
     * 主键
     */
    @TableId("rule_action_rel_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long ruleActionRelId;
    /**
     * 动作
     */
	@TableField("action_id")
	@ApiModelProperty(required= true,value = "动作")
	private Long actionId;
    /**
     * 规则
     */
	@TableField("rule_id")
	@ApiModelProperty(required= true,value = "规则")
	private Long ruleId;
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
		return this.ruleActionRelId;
	}

	@Override
	public String toString() {
		return "ActionRuleRel{" +
			"ruleActionRelId=" + ruleActionRelId +
			", actionId=" + actionId +
			", ruleId=" + ruleId +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
