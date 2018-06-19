package com.ht.rule.common.api.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则动作定义信息
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
public class ActionInfoVo {

	private Long value;
	private String text;
	/**
	 * 参数值
	 */
    private List<ActionParamInfoVo> paramInfoList;

	/**
     * 主键
     */
	@ApiModelProperty(required= true,value = "主键")
	private Long actionId;
    /**
     * 动作类型(1实现2自身)
     */
	@ApiModelProperty(required= true,value = "动作类型(1实现2自身)")
	private Integer actionType;
    /**
     * 动作名称
     */
	@ApiModelProperty(required= true,value = "动作名称")
	private String actionName;
    /**
     * 动作描述
     */
	@ApiModelProperty(required= true,value = "动作描述")
	private String actionDesc;
    /**
     * 动作实现类(包路径)
     */
	@ApiModelProperty(required= true,value = "动作实现类(包路径)")
	private String actionClass;
    /**
     * 是否有效
     */
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
	@ApiModelProperty(required= true,value = "创建时间")
	private Date creTime;
    /**
     * 备注
     */
	@ApiModelProperty(required= true,value = "备注")
	private String remark;


	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ActionParamInfoVo> getParamInfoList() {
		return paramInfoList;
	}

	public void setParamInfoList(List<ActionParamInfoVo> paramInfoList) {
		this.paramInfoList = paramInfoList;
	}
}
