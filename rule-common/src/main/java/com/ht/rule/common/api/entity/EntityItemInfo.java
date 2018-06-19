package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ht.rule.common.api.entity.enums.DataTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体属性信息
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_entity_item_info")
@Data
public class EntityItemInfo extends Model<EntityItemInfo> {


	public EntityItemInfo(){}
	public EntityItemInfo(String itemIdentify){
		this.itemIdentify = itemIdentify;
	}
    private static final long serialVersionUID = 1L;
	@TableField(exist = false)
    private EntityInfo entityInfo;
    /**
     * 主键
     */
    @TableId("item_id")
	@ApiModelProperty(value = "主键")
	private Long itemId;
    /**
     * 实体id
     */
	@TableField("entity_id")
	@ApiModelProperty(value = "实体id")
	private Long entityId;
    /**
     * 字段名称
     */
	@TableField("item_name")
	@ApiModelProperty(value = "字段名称")
	private String itemName;
	@TableField(exist = false)
	private String entityIdentify; //实体标识


	@TableField("item_identify")
	@ApiModelProperty(value = "字段标识")
	private String itemIdentify;
    /**
     * 属性描述
     */
	@TableField("item_desc")
	@ApiModelProperty(value = "属性描述")
	private String itemDesc;

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}

	@TableField("data_type")
	private DataTypeEnum dataType;

	@TableField("constant_id")
	@ApiModelProperty(value = "常量id")
	private Long constantId;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	/**
     * 创建人
     */
	@TableField("cre_user_id")
	@ApiModelProperty(value = "创建人")
	private String creUserId;
    /**
     * 创建时间
     */
	@TableField("cre_time")
	@ApiModelProperty(value = "创建时间")
	private Date creTime;
    /**
     * 是否有效
     */
	@TableField("is_effect")
	@ApiModelProperty(value = "是否有效")
	private Integer isEffect;
    /**
     * 备注
     */
	@ApiModelProperty(value = "备注")
	private String remark;
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}


	@Override
	protected Serializable pkVal() {
		return this.itemId;
	}
	@Override
	public String toString() {
		return "EntityItemInfo{" +
			"itemId=" + itemId +
			", entityId=" + entityId +
			", itemName=" + itemName +
			", itemIdentify=" + itemIdentify +
			", itemDesc=" + itemDesc +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", isEffect=" + isEffect +
			", remark=" + remark +
			"}";
	}
}
