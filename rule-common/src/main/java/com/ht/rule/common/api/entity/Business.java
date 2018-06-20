package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @author 张鹏
 * @since 2018-04-04
 */
@Data
@ApiModel
@TableName("rule_business_type")
public class Business extends Model<Business> {

	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private BusinessSystem businessSystem;
	/**
	 * 业务线id
	 */
	@TableId(value = "business_type_id",type = IdType.UUID)
	@ApiModelProperty(required= true,value = "业务线id")
	private String businessTypeId;
	/**
	 * 业务线名称
	 */
	@TableField("business_type_name")
	@ApiModelProperty(required= true,value = "业务线名称")
	private String businessTypeName;
	/**
	 * 业务线归属系统
	 */
	@TableField("business_system_id")
	@ApiModelProperty(required= true,value = "业务线归属系统")
	private String belongSystemLocalId;
	/**
	 * 业务线代码
	 */
	@TableField("business_type_code")
	@ApiModelProperty(required= true,value = "业务线代码")
	private String businessTypeCode;
	/**
	 * 创建人
	 */
	@TableField("create_user_id")
	@ApiModelProperty(required= true,value = "创建人")
	private String createUserId;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	@ApiModelProperty(required= true,value = "创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新人
	 */
	@TableField("update_user_id")
	@ApiModelProperty(required= true,value = "更新人")
	private String updateUserId;
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	@ApiModelProperty(required= true,value = "更新时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private Date updateTime;


	public static final String BUSINESS_TYPE_ID = "business_type_id";

	public static final String BUSINESS_TYPE_NAME = "business_type_name";

	public static final String BELONG_SYSTEM_LOCAL_ID = "business_system_id";

	public static final String BUSINESS_TYPE_CODE = "business_type_code";

	public static final String CREATE_USER_ID = "create_user_id";

	public static final String CREATE_TIME = "create_time";

	public static final String UPDATE_USER_ID = "update_user_id";

	public static final String UPDATE_TIME = "update_time";

	@Override
	protected Serializable pkVal() {
		return this.businessTypeId;
	}

}
