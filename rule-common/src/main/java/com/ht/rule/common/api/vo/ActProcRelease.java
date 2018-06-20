package com.ht.rule.common.api.vo;

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

/**
 * <p>
 * <p>
 * </p>
 *
 * @author hzm
 * @since 2018-01-17
 */
@Data
@ApiModel
@TableName("act_proc_release")
public class ActProcRelease extends Model<ActProcRelease> implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 返回结果用
     */
    @TableField(exist = false)
    private List<ModelSence> variableMap;


    /**
     * 用于展示验证状态
     */
    @TableField(exist = false)
    private String IsValidateAlia;

    /**
     * 用于展示验证状态
     */
    @TableField(exist = false)
    private String versionTypeAlia;

    /**
     * 用于展示验证状态
     */
    @TableField(exist = false)
    private String betaVersion;

    /**
     * 用于展示验证状态
     */
    @TableField(exist = false)
    private String releaseVersion;

    /**
     * 用于展示审核状态
     */
    @TableField(exist = false)
    private String isApprovedAlia;

    /**
     * 用于展示有效状态
     */
    @TableField(exist = false)
    private String isEffectAlia;

    /**
     * 主键
     */
    @TableId("ID")
    @ApiModelProperty(required = true, value = "主键")
    private Long id;
    /**
     * 模型定义ID，与 ACT_RE_PROCDEF.ID_ 关联,ACT_RE_PROCDEF 表中有模型部署id
     */
    @TableField("MODEL_PROCDEF_ID")
    @ApiModelProperty(required = true, value = "模型定义ID，与 ACT_RE_PROCDEF.ID_ 关联,ACT_RE_PROCDEF 表中有模型部署id")
    private String modelProcdefId;
    /**
     * 模型名称
     */
    @TableField("MODEL_NAME")
    @ApiModelProperty(required = true, value = "模型名称")
    private String modelName;
    /**
     * 模型版本
     */
    @TableField("MODEL_VERSION")
    @ApiModelProperty(required = true, value = "模型版本")
    private String modelVersion;
    /**
     * 模型分类
     */
    @TableField("MODEL_CATEGORY")
    @ApiModelProperty(required = true, value = "模型分类")
    private String modelCategory;
    /**
     * 版本类型，0-测试版，1-正式版
     */
    @TableField("VERSION_TYPE")
    @ApiModelProperty(required = true, value = "版本类型，0-测试版，1-正式版")
    private String versionType;
    /**
     * 是否验证通过： 0-待验证，1-验证通过，2-验证不通过；默认为0;
     */
    @TableField("IS_VALIDATE")
    @ApiModelProperty(required = true, value = "是否验证通过： 0-待验证，1-验证通过，2-验证不通过；默认为0;")
    private String isValidate;
    /**
     * 是否审核通过：0-待审核，1-审核通过，2-审核不通过；默认为0;
     */
    @TableField("IS_APPROVE")
    @ApiModelProperty(required = true, value = "是否审核通过：0-待审核，1-审核通过，2-审核不通过；默认为0;")
    private String isApprove;
    /**
     * 是否生效：0-有效，1-无效
     */
    @TableField("IS_EFFECT")
    @ApiModelProperty(required = true, value = "是否生效：0-有效，1-无效")
    private String isEffect;
    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    @ApiModelProperty(required = true, value = "更新时间")
    private Date updateTime;
    /**
     * 更新用户
     */
    @TableField("UPDATE_USER")
    @ApiModelProperty(required = true, value = "更新用户")
    private String updateUser;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ApiModelProperty(required = true, value = "创建时间")
    private Date createTime;
    /**
     * 创建用户
     */
    @TableField("CREATE_USER")
    @ApiModelProperty(required = true, value = "创建用户")
    private String createUser;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @TableField(exist=false)
    @ApiModelProperty(required = false, value = "模型任务id")
    private String taskId;
    
    @TableField(exist=false)
    @ApiModelProperty(required = false, value = "任务状态1停止2启动")
    private String taskStatus;
    
    @TableField(exist=false)
    @ApiModelProperty(required = false, value = "corn")
    private String cornText;


}
