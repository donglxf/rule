package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则信息
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_info")
@Data
public class RuleInfo extends Model<RuleInfo> {

    private static final long serialVersionUID = 1L;

	@TableField(exist = false)
    private List<ConditionInfo> cons;
	@TableField(exist = false)
	private List<ActionRuleRel> actionRels;
	@TableField(exist = false)
	private RuleGroup group ;

	/**
     * 主键
     */
    @TableId(value = "rule_id",type = IdType.AUTO)
	@ApiModelProperty(required= true,value = "主键")
	private Long ruleId;
    /**
     * 场景
     */
	@TableField("scene_id")
	@ApiModelProperty(required= true,value = "场景")
	private Long sceneId;
    /**
     * 名称
     */
	@TableField("rule_name")
	@ApiModelProperty(required= true,value = "名称")
	private String ruleName;
    /**
     * 描述
     */
	@TableField("rule_desc")
	@ApiModelProperty(required= true,value = "描述")
	private String ruleDesc;
    /**
     * 是否启用
     */
	@TableField("rule_enabled")
	@ApiModelProperty(required= true,value = "是否启用")
	private Integer ruleEnabled;
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
		return this.ruleId;
	}

	@Override
	public String toString() {
		return "Info{" +
			"ruleId=" + ruleId +
			", sceneId=" + sceneId +
			", ruleName=" + ruleName +
			", ruleDesc=" + ruleDesc +
			", ruleEnabled=" + ruleEnabled +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
