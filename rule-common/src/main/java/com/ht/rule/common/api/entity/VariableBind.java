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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-17
 */
@Data
@ApiModel
@TableName("rule_variable_bind")
public class VariableBind extends Model<VariableBind> {

    private static final long serialVersionUID = 1L;

	/**
	 * 变量列表
	 */
    @TableField(exist = false)
    private List<Map<String, String>> optionData;

    /**
     * 主键,流水号
     */
    @TableId("id")
	@ApiModelProperty(required= true,value = "主键,流水号")
	private Long id;
    /**
     * 決策版本流水
     */
	@TableField("sence_version_id")
	@ApiModelProperty(required= true,value = "決策版本流水")
	private Long senceVersionId;
    /**
     * 变量编码
     */
	@TableField("variable_code")
	@ApiModelProperty(required= true,value = "变量编码")
	private String variableCode;
    /**
     * 变量名称
     */
	@TableField("variable_name")
	@ApiModelProperty(required= true,value = "变量名称")
	private String variableName;
    /**
     * 变量类型，与RULE_ENTITY_ITEM_INFO.DATA_TYPE 一致
     */
	@TableField("data_type")
	@ApiModelProperty(required= true,value = "变量类型，与RULE_ENTITY_ITEM_INFO.DATA_TYPE 一致")
	private String dataType;
    /**
     * 常量ID，与RULE_ENTITY_ITEM_INFO.CONSTANT_ID 一致
     */
	@TableField("constant_id")
	@ApiModelProperty(required= true,value = "常量ID，与RULE_ENTITY_ITEM_INFO.CONSTANT_ID 一致")
	private Long constantId;
    /**
     * 绑定数据表
     */
	@TableField("bind_table")
	@ApiModelProperty(required= true,value = "绑定数据表")
	private String bindTable;
    /**
     * 绑定数据表字段
     */
	@TableField("bind_column")
	@ApiModelProperty(required= true,value = "绑定数据表字段")
	private String bindColumn;
    /**
     * 是否生效：0-有效，1-无效
     */
	@TableField("is_effect")
	@ApiModelProperty(required= true,value = "是否生效：0-有效，1-无效")
	private String isEffect;
    /**
     * 用户输入值，只保存最后一次的
     */
	@TableField("tmp_value")
	@ApiModelProperty(required= true,value = "用户输入值，只保存最后一次的")
	private String tmpValue;
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


	private transient  String senceCode;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
