package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-01-03
 */
@ApiModel
@Data
@TableName("rule_scene_version a where not  EXISTS  ( select * from RISK_VARIABLE_BIND b where b.SENCE_VERSIONID=a.version_id )")
public class SceneVersion2 extends Model<SceneVersion2> {

    private static final long serialVersionUID = 1L;

    /**
     * 版本记录id
     */
	@TableId(value="version_id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "版本记录id")
	private Long versionId;
    /**
     * 版本号 
     */
	@ApiModelProperty(required= true,value = "版本号 ")
	private String version;
    /**
     * 类型：1决策或评分卡2模型
     */
	@ApiModelProperty(required= true,value = "类型：1决策或评分卡2模型")
	private Integer type;
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
	@TableField("scene_id")
	@ApiModelProperty(required= true,value = "业务id")
	private String sceneId;
    /**
     * 创建时间
     */
	@TableField("cre_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date creTime;
    /**
     * 创建用户
     */
	@TableField("cre_user_id")
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
	@TableField("`status`")
	@ApiModelProperty(required= true,value = "状态1正常 -1 禁用")
	private Integer status;


	@Override
	protected Serializable pkVal() {
		return this.versionId;
	}

	@Override
	public String toString() {
		return "SceneVersion{" +
			"versionId=" + versionId +
			", version=" + version +
			", type=" + type +
			", title=" + title +
			", comment=" + comment +
			", sceneId=" + sceneId +
			", creTime=" + creTime +
			", creUserId=" + creUserId +
			", ruleDiv=" + ruleDiv +
			", ruleDrl=" + ruleDrl +
			"}";
	}
}
