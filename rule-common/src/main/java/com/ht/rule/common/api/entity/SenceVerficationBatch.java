package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-01-24
 */
@ApiModel
@Data
@TableName("rule_sence_verfication_batch")
public class SenceVerficationBatch extends Model<SenceVerficationBatch> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,批次号
     */
	@ApiModelProperty(required= true,value = "主键,批次号")
	private Long id;
    /**
     * 決策版本流水
     */
	@TableField("sence_version_id")
	@ApiModelProperty(required= true,value = "決策版本流水")
	private String senceVersionId;
    /**
     * 批次大小
     */
	@TableField("batch_size")
	@ApiModelProperty(required= true,value = "批次大小")
	private Integer batchSize;
    /**
     * 是否生效：0-手动，1-自动
     */
	@TableField("verfication_type")
	@ApiModelProperty(required= true,value = "是否生效：0-手动，1-自动")
	private String verficationType;
    /**
     * 创建时间
     */
	@TableField("create_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date createTime;
    /**
     * 创建用户
     */
	@TableField("create_user")
	@ApiModelProperty(required= true,value = "创建用户")
	private String createUser;
    /**
     * 结束时间
     */
	@TableField("end_time")
	@ApiModelProperty(required= true,value = "结束时间")
	private Date endTime;



	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SenceVerficationBatch{" +
			"id=" + id +
			", senceVersionId=" + senceVersionId +
			", batchSize=" + batchSize +
			", verficationType=" + verficationType +
			", createTime=" + createTime +
			", createUser=" + createUser +
			", timerStop=" + endTime +
			"}";
	}
}
