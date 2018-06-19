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
 * 动作参数信息表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_action_param_info")
@Data
public class ActionParamInfo extends Model<ActionParamInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("action_param_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long actionParamId;
    /**
     * 动作id
     */
	@TableField("action_id")
	@ApiModelProperty(required= true,value = "动作id")
	private Long actionId;
    /**
     * 参数名称
     */
	@TableField("action_param_name")
	@ApiModelProperty(required= true,value = "参数名称")
	private String actionParamName;
    /**
     * 参数描述
     */
	@TableField("action_param_desc")
	@ApiModelProperty(required= true,value = "参数描述")
	private String actionParamDesc;
    /**
     * 标识
     */
	@TableField("param_identify")
	@ApiModelProperty(required= true,value = "标识")
	private String paramIdentify;
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
		return this.actionParamId;
	}

	@Override
	public String toString() {
		return "ActionParamInfo{" +
			"actionParamId=" + actionParamId +
			", actionId=" + actionId +
			", actionParamName=" + actionParamName +
			", actionParamDesc=" + actionParamDesc +
			", paramIdentify=" + paramIdentify +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
