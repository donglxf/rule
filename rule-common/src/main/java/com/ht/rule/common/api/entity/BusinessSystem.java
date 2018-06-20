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
import java.util.List;

/**
 * <p>
 * 业务系统表
 * </p>
 *
 * @author 张鹏
 * @since 2018-04-08
 */
@Data
@ApiModel
@TableName("rule_business_system")
public class BusinessSystem extends Model<BusinessSystem> {

    private static final long serialVersionUID = 1L;
	@TableField(exist = false)
    private List<Business> businessList;
    /**
     * 业务系统在派单系统中id
     */
    @TableId( value = "business_system_id",type = IdType.UUID)
	@ApiModelProperty(required= true,value = "业务系统在派单系统中id")

	private String businessSystemlId;
    /**
     * 业务系统自身id
     */
	@TableField("remote_business_system_id")
	@ApiModelProperty(required= true,value = "业务系统自身id")
	private String remoteBusinessSystemId;
    /**
     * 业务系统编码
     */
	@TableField("business_system_code")
	@ApiModelProperty(required= true,value = "业务系统编码")
	private String businessSystemCode;
    /**
     * 业务系统名称
     */
	@TableField("business_system_name")
	@ApiModelProperty(required= true,value = "业务系统名称")
	private String businessSystemName;
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


	public static final String BUSINESS_SYSTEML_ID = "business_system_id";

	public static final String REMOTE_BUSINESS_SYSTEM_ID = "remote_business_system_id";

	public static final String BUSINESS_SYSTEM_CODE = "business_system_code";

	public static final String BUSINESS_SYSTEM_NAME = "business_system_name";

	public static final String CREATE_USER_ID = "create_user_id";

	public static final String CREATE_TIME = "create_time";

	public static final String UPDATE_USER_ID = "update_user_id";

	public static final String UPDATE_TIME = "update_time";

	@Override
	protected Serializable pkVal() {
		return this.businessSystemlId;
	}

}
