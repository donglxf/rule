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
 * 规则引擎使用场景
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_scene_version v left join rule_scene_info s on v.scene_id = s.scene_id")
@Data
public class SceneInfoVersion extends Model<SceneInfoVersion> {

    private static final long serialVersionUID = 1L;
    /**
     * 标识
     */
	@TableField("s.scene_identify")
	@ApiModelProperty(required= true,value = "标识")
	private String sceneIdentify;
    /**
     * 类型(暂不使用)
     */
	@TableField("scene_type")
	@ApiModelProperty(required= true,value = "类型(暂不使用)")
	private Integer sceneType;
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


	@TableField("business_id")
	@ApiModelProperty(required= true,value = "业务线id")
	private String businessId;
    /**
     * 是否有效
     */
	@TableField("is_effect")
	@ApiModelProperty(required= true,value = "策略是否有效")
	private Integer isEffect;
    /**
     * 备注
     */
	@ApiModelProperty(required= true,value = "备注")
	private String remark;
	@ApiModelProperty(required= true,value = "版本是否有效")
	private Integer status ;
	/**
	 * 版本记录id
	 */
	@TableId(value="version_id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "版本记录id")
	private Long versionId;
	/**
	 * 版本号
	 */
	@TableField("v.`version`")
	@ApiModelProperty(required= true,value = "版本号 ")
	private String version;
	/**
	 * 类型：1决策或评分卡2模型
	 */
	@ApiModelProperty(required= true,value = "类型：0测试版 1正式版")
	private Integer type;



	/**
	 * 正式版本号
	 */
	@TableField("official_version")
	@ApiModelProperty(required = true, value = "正式版本号")
	private String officialVersion;


	@TableField("test_status")
	@ApiModelProperty(required= true,value = "测试是否通过，1-通过，0-未通过")
	private Integer testStatus;
	/**
	 * 标题
	 */
	@ApiModelProperty(required= true,value = "标题")
	private String title;
	/**
	 * 详细描述
	 */
	@ApiModelProperty(required= true,value = "详细描述")
	private String comment;
	/**
	 * 业务id
	 */
	@TableField("v.scene_id")
	@ApiModelProperty(required= true,value = "业务id")
	private Long sceneId;
	/**
	 * 创建时间
	 */
	@TableField("v.cre_time")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date creTime;
	/**
	 * 创建用户
	 */
	@TableField("v.cre_user_id")
	@ApiModelProperty(required= true,value = "创建用户")
	private String creUserId;
	/**
	 * 规则html
	 */
	@TableField("rule_div")
	@ApiModelProperty(required= true,value = "规则html")
	private String ruleDiv;
	/**
	 * rule文件内容
	 */
	@TableField("rule_drl")
	@ApiModelProperty(required= true,value = "rule文件内容")
	private String ruleDrl;


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
