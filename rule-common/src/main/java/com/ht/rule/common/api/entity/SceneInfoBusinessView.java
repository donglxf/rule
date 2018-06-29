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
 * 规则引擎使用场景
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
//@TableName("rule_scene_info")
@TableName("rule_scene_info s LEFT JOIN rule_business_type t on s.business_id = t.business_type_id LEFT JOIN rule_business_system ss on ss.business_system_id = t.business_system_id")
@Data
public class SceneInfoBusinessView extends Model<SceneInfoBusinessView> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "scene_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long sceneId;

    /**
     * 标识
     */
	@TableField("scene_identify")
	@ApiModelProperty(required= true,value = "标识")
	private String sceneIdentify;

	@TableField("business_type_name")
	@ApiModelProperty(required= true,value = "标识")
	private String businessTypeName;

	@TableField("business_type_code")
	@ApiModelProperty(required= true,value = "标识")
	private String businessTypeCode;

	@TableField("business_system_code")
	@ApiModelProperty(required= true,value = "标识")
	private String businessSystemCode;

	@TableField("business_system_name")
	@ApiModelProperty(required= true,value = "标识")
	private String businessSystemName;
    /**
     * 类型(暂不使用)
     */
	@TableField("scene_type")
	@ApiModelProperty(required= true,value = "类型(暂不使用)")
	private Integer sceneType;

	@TableField("business_id")
	@ApiModelProperty(required= true,value = "业务线id")
	private String businessId;
    /**
     * 名称
     */
	@TableField("scene_name")
	@ApiModelProperty(required= true,value = "名称")
	private String sceneName;
    /**
     * 描述
     */
	@TableField("scene_desc")
	@ApiModelProperty(required= true,value = "描述")
	private String sceneDesc;
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
		return this.sceneId;
	}

	@Override
	public String toString() {
		return "SceneInfo{" +
			"sceneId=" + sceneId +
			", sceneIdentify=" + sceneIdentify +
			", sceneType=" + sceneType +
			", sceneName=" + sceneName +
			", sceneDesc=" + sceneDesc +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
