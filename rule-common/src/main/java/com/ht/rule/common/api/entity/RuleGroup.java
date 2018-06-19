package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-16
 */
@ApiModel
@TableName("rule_group")
public class RuleGroup extends Model<RuleGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则分组id
     */
	@TableId(value="rule_group_id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "规则分组id")
	private Long ruleGroupId;
    /**
     * 规则id
     */
	@TableField("rule_id")
	@ApiModelProperty(required= true,value = "规则id")
	private Long ruleId;
    /**
     * 序号，排序
     */
	@ApiModelProperty(required= true,value = "序号，排序")
	private Integer index;
    /**
     * 更新时间
     */
	@ApiModelProperty(required= true,value = "更新时间")
	private Date updateTime;
    /**
     * 权值
     */
	@ApiModelProperty(required= true,value = "权值")
	private Double weight;
    /**
     * 场景id
     */
	@TableField("scene_id")
	@ApiModelProperty(required= true,value = "场景id")
	private Long sceneId;
    /**
     * 名称
     */
	@ApiModelProperty(required= true,value = "名称")
	private String name;


	public Long getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(Long ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getSceneId() {
		return sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Serializable pkVal() {
		return this.ruleGroupId;
	}

	@Override
	public String toString() {
		return "RuleGroup{" +
			"ruleGroupId=" + ruleGroupId +
			", ruleId=" + ruleId +
			", index=" + index +
			", updateTime=" + updateTime +
			", weight=" + weight +
			", sceneId=" + sceneId +
			", name=" + name +
			"}";
	}
}
