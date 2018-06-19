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
 * @author dyb
 * @since 2017-12-29
 */
@ApiModel
@TableName("rule_constant_info")
@Data
public class ConstantInfo extends Model<ConstantInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId ("con_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long conId;
    /**
     * 常量类别
     */
	@TableField("con_key")
	@ApiModelProperty(required= true,value = "常量类别")
	private String conKey;
    /**
     * 常量名
     */
	@TableField("con_name")
	@ApiModelProperty(required= true,value = "常量名")
	private String conName;
    /**
     * 常量类型
     */
	@TableField("con_type")
	@ApiModelProperty(required= true,value = "常量类型")
	private String conType;
    /**
     * 变量code
     */
	@TableField("con_code")
	@ApiModelProperty(required= true,value = "变量code")
	private String conCode;

	@TableField("business_id")
	@ApiModelProperty(required= true,value = "业务线id")
	private String businessId;
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
		return this.conId;
	}

	@Override
	public String toString() {
		return "ConstantInfo{" +
			"conId=" + conId +
			", conKey=" + conKey +
			", conName=" + conName +
			", conType=" + conType +
			", conCode=" + conCode +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
