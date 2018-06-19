package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 规则执行日志表
 * </p>
 *
 * @author 张鹏
 * @since 2018-06-19
 */
@Data
@ApiModel
@TableName("rule_drools_log")
public class DroolsLog extends Model<DroolsLog> {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(required= true,value = "")
	private Long id;
    /**
     * 验证批次号
     */
	@TableField("batch_id")
	@ApiModelProperty(required= true,value = "验证批次号")
	private Long batchId;
    /**
     * 決策版本流水
     */
	@TableField("sence_versionid")
	@ApiModelProperty(required= true,value = "決策版本流水")
	private String senceVersionid;
    /**
     * 入参
     */
	@TableField("in_paramter")
	@ApiModelProperty(required= true,value = "入参")
	private String inParamter;
    /**
     * 计算结果
     */
	@TableField("out_paramter")
	@ApiModelProperty(required= true,value = "计算结果")
	private String outParamter;
    /**
     * 命中规则总数
     */
	@TableField("execute_total")
	@ApiModelProperty(required= true,value = "命中规则总数")
	private Integer executeTotal;
    /**
     * 插入时间
     */
	@TableField("create_time")
	@ApiModelProperty(required= true,value = "插入时间")
	private Date createTime;
    /**
     * 执行时间
     */
	@TableField("execute_time")
	@ApiModelProperty(required= true,value = "执行时间")
	private Long executeTime;
    /**
     * 决策名
     */
	@TableField("sence_name")
	@ApiModelProperty(required= true,value = "决策名")
	private String senceName;
    /**
     * 版本号
     */
	@TableField("version_num")
	@ApiModelProperty(required= true,value = "版本号")
	private String versionNum;


	public static final String ID = "id";

	public static final String BATCH_ID = "batch_id";

	public static final String SENCE_VERSIONID = "sence_versionid";

	public static final String IN_PARAMTER = "in_paramter";

	public static final String OUT_PARAMTER = "out_paramter";

	public static final String EXECUTE_TOTAL = "execute_total";

	public static final String CREATE_TIME = "create_time";

	public static final String EXECUTE_TIME = "execute_time";

	public static final String SENCE_NAME = "sence_name";

	public static final String VERSION_NUM = "version_num";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
