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
 * <p>
 * </p>
 *
 * @author 张鹏
 * @since 2018-01-03
 */
@ApiModel
@TableName("rule_scene_version")
@Data
public class SceneVersion extends Model<SceneVersion> {

    private static final long serialVersionUID = 1L;

    /**
     * 版本记录id
     */
    @TableId(value = "version_id",type= IdType.ID_WORKER)
    @ApiModelProperty(required = true, value = "版本记录id")
    private Long versionId;
    /**
     * 版本号
     */
    @ApiModelProperty(required = true, value = "版本号 ")
    private String version;

    /**
     * 正式版本号
     */
    @TableField("official_version")
    @ApiModelProperty(required = true, value = "正式版本号")
    private String officialVersion;

    /**
     * 类型：1决策或评分卡2模型
     */
    @ApiModelProperty(required = true, value = "类型：0测试版 1正式版")
    private Integer type;

    /**
     * 标题
     */
    @ApiModelProperty(required = true, value = "标题")
    private String title;
    /**
     * 详细描述
     */
    @ApiModelProperty(required = true, value = "详细描述")
    private String comment;
    /**
     * 业务id
     */
    @TableField("scene_id")
    @ApiModelProperty(required = true, value = "场景id")
    private Long sceneId;

    /**
     * 业务id
     */
    @TableField("scene_identify")
    @ApiModelProperty(required = true, value = "场景code ")
    private String sceneIdentify;


    /**
     * 创建时间
     */
    @TableField("cre_time")
    @ApiModelProperty(required = true, value = "创建时间")
    private Date creTime;
    /**
     * 创建用户
     */
    @TableField("cre_user_id")
    @ApiModelProperty(required = true, value = "创建用户")
    private String creUserId;
    /**
     * 规则html
     */
    @TableField("rule_div")
    @ApiModelProperty(required = true, value = "规则html")
    private String ruleDiv;
    /**
     * rule文件内容
     */
    @TableField("rule_drl")
    @ApiModelProperty(required = true, value = "rule文件内容")
    private String ruleDrl;
    @TableField("`status`")
    @ApiModelProperty(required = true, value = "1启用 0 禁用")
    private Integer status;

    /**
     * 测试是否通过，1-通过，0-未通过
     */
    @TableField("test_status")
    @ApiModelProperty(required = true, value = "测试是否通过，1-通过，0-未通过")
    private Integer testStatus;

    /**
     * 业务类型，1-评分卡，2-决策表
     */
    @TableField("business_type")
    @ApiModelProperty(required = true, value = "业务类型，1-评分卡，2-决策表")
    private String businessType;
    /**
     * 业务线，1-房速贷，2-现金贷
     */
    @TableField("business_line")
    @ApiModelProperty(required = true, value = "业务线，1-房速贷，2-现金贷")
    private String businessLine;
    /**
     * 是否绑定变量，1-绑定，0-未绑定
     */
    @TableField("is_bind_var")
    @ApiModelProperty(required = true, value = "是否绑定变量，1-绑定，0-未绑定")
    private String isBindVar;

    @Override
    protected Serializable pkVal() {
        return this.versionId;
    }


    @Override
    public String toString() {
        return "SceneVersion{" +
                "versionId=" + versionId +
                ", version=" + version +
                ", officialVersion=" + officialVersion +
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
