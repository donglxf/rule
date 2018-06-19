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

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-29
 */
@ApiModel
@TableName("rule_action_version")
@Data
public class RuleActionVersion extends Model<RuleActionVersion> {

    private static final long serialVersionUID = 1L;

    /**
     * 动作版本关联id
     */
	@TableId(value="rule_action_version_id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "动作版本关联id")
	private Long riskRuleActionVersionId;
    /**
     * 版本id
     */
	@TableField("version_id")
	@ApiModelProperty(required= true,value = "版本id")
	private Long versionId;
    /**
     * 实体类的名
     */
	@TableField("action_class")
	@ApiModelProperty(required= true,value = "实体类的名")
	private String actionClass;
    /**
     * 动作id
     */
	@TableField("action_id")
	@ApiModelProperty(required= true,value = "动作id")
	private Long actionId;

	@TableField(exist = false)
	private String actionClazzIdentify;



	@Override
	protected Serializable pkVal() {
		return this.riskRuleActionVersionId;
	}


	public String getActionClazzIdentify() {
		int index = actionClass.lastIndexOf(".");
		return actionClass.substring(index + 1).substring(0, 1).toLowerCase() +
				actionClass.substring(index + 1).substring(1);
	}
	@Override
	public String toString() {
		return "RuleActionVersion{" +
			"riskRuleActionVersionId=" + riskRuleActionVersionId +
			", versionId=" + versionId +
			", actionClass=" + actionClass +
			", actionId=" + actionId +
			"}";
	}
}
