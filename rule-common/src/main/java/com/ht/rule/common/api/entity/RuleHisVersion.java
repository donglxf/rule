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
 * 
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-17
 */
@ApiModel
@TableName("rule_his_version")
@Data
public class RuleHisVersion extends Model<RuleHisVersion> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
	@ApiModelProperty(required= true,value = "主键")
	private Long id;
    /**
     * 決策版本流水
     */
	@TableField("sence_version_id")
	@ApiModelProperty(required= true,value = "決策版本流水")
	private Long senceVersionId;
    /**
     * 规则名称
     */
	@TableField("rule_name")
	@ApiModelProperty(required= true,value = "规则名称")
	private String ruleName;
    /**
     * 规则描述
     */
	@TableField("rule_desc")
	@ApiModelProperty(required= true,value = "规则描述")
	private String ruleDesc;
    /**
     * 是否生效：0-有效，1-无效
     */
	@TableField("is_effect")
	@ApiModelProperty(required= true,value = "是否生效：0-有效，1-无效")
	private String isEffect;
    /**
     * 创建用户
     */
	@TableField("create_user")
	@ApiModelProperty(required= true,value = "创建用户")
	private String createUser;
    /**
     * 创建时间
     */
	@TableField("create_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date createTime;



	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RuleHisVersion{" +
			"id=" + id +
			", senceVersionid=" + senceVersionId +
			", ruleName=" + ruleName +
			", ruleDesc=" + ruleDesc +
			", isEffect=" + isEffect +
			", createUser=" + createUser +
			", createTime=" + createTime +
			"}";
	}
}
