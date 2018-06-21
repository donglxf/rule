package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ht.rule.common.api.entity.enums.EntityTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则引擎实体信息表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_entity_info")
@Data
public class EntityInfo extends Model<EntityInfo> {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId("entity_id")
    @ApiModelProperty( value = "主键")
    private Long entityId;

    @TableField("business_id")
    @ApiModelProperty(required= true,value = "业务线id")
    private String businessId;

    /**
     * 名称
     */
    @TableField("entity_name")
    @ApiModelProperty( value = "名称")
    private String entityName;

    /**
     * 描述
     */
    @TableField("entity_desc")
    @ApiModelProperty( value = "描述")
    private String entityDesc;
    /**
     * 标识
     */
    @TableField("entity_identify")
    @ApiModelProperty( value = "标识")
    private String entityIdentify;
    /**
     * 包路径
     */
    @TableField("pkg_name")
    @ApiModelProperty( value = "包路径")
    private String pkgName;


   /* @TableField("type")
    @ApiModelProperty( value = "对象类型")
    private EntityTypeEnum type;*/

    /**
     * 创建人
     */
    @TableField("cre_user_id")
    @ApiModelProperty( value = "创建人")
    private String creUserId;
    /**
     * 创建时间
     */
    @TableField("cre_time")
    @ApiModelProperty( value = "创建时间")
    private Date creTime;
    /**
     * 是否有效(1是0否)
     */
    @TableField("is_effect")
    @ApiModelProperty( value = "是否有效(1是0否)")
    private Integer isEffect;
    /**
     * 备注
     */
    @ApiModelProperty( value = "备注")
    private String remark;

    @TableField(exist=false)
    @ApiModelProperty(required = false, value = "变量集合")
    private List<EntityItemInfo> items;



    @Override
    protected Serializable pkVal() {
        return this.entityId;
    }

    @Override
    public String toString() {
        return "EntityInfo{" +
                "entityId=" + entityId +
                ", entityName=" + entityName +
                ", entityDesc=" + entityDesc +
                ", entityIdentify=" + entityIdentify +
                ", pkgName=" + pkgName +
                ", creUserId=" + creUserId +
                ", creTime=" + creTime +
                ", isEffect=" + isEffect +
                ", remark=" + remark +
                "}";
    }

    /**
     * 方法说明:获取实体类名称
     */
    public String getEntityClazz() {
       // int index = pkgName.lastIndexOf(".");
      //  return pkgName.substring(index + 1);
        return "11";
    }
}
