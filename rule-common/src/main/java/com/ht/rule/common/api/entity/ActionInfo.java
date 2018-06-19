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

/**
 * <p>
 * 规则动作定义信息
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_action_info")
@Data
public class ActionInfo extends Model<ActionInfo> {

    private static final long serialVersionUID = 1L;

    /**
	 * 参数值
	 */
	@TableField(exist = false)
    private List<ActionParamValueInfo> paramValueInfoList;

	public List<ActionParamValueInfo> getParamValueInfoList() {
		return paramValueInfoList;
	}

	public void setParamValueInfoList(List<ActionParamValueInfo> paramValueInfoList) {
		this.paramValueInfoList = paramValueInfoList;
	}

	/**
     * 主键
     */
    @TableId("action_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long actionId;

	@TableField("business_id")
	@ApiModelProperty(required= true,value = "业务线id")
	private String businessId;

	@TableField(exist = false)
	private Long ruleActionRelId;
    /**
     * 动作类型(1实现2自身)
     */
	@TableField("action_type")
	@ApiModelProperty(required= true,value = "动作类型(1实现2自身)")
	private Integer actionType;
    /**
     * 动作名称
     */
	@TableField("action_name")
	@ApiModelProperty(required= true,value = "动作名称")
	private String actionName;
    /**
     * 动作描述
     */
	@TableField("action_desc")
	@ApiModelProperty(required= true,value = "动作描述")
	private String actionDesc;
    /**
     * 动作实现类(包路径)
     */
	@TableField("action_class")
	@ApiModelProperty(required= true,value = "动作实现类(包路径)")
	private String actionClass;
	/**
     * 动作实现类(包路径)
     */
	@TableField("action_method")
	@ApiModelProperty(required= true,value = "动作执行方法名")
	private String actionMethod;
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
	@TableField("remark")
	@ApiModelProperty(required= true,value = "备注")
	private String remark;

	/**
     * 动作说明，用于满足规则后输出提示信息
     */
	@TableField("action_out_desc")
	@ApiModelProperty(required= true,value = "动作说明，用于满足规则后输出提示信息")
	private String actionOutDesc;




//	public String getActionOutDesc() {
//		return actionOutDesc;
//	}
//
//	public void setActionOutDesc(String actionOutDesc) {
//		this.actionOutDesc = actionOutDesc;
//	}

	@Override
	protected Serializable pkVal() {
		return this.actionId;
	}

	@Override
	public String toString() {
		return "ActionInfo{" +
			"actionId=" + actionId +
			", actionType=" + actionType +
			", actionName=" + actionName +
			", actionDesc=" + actionDesc +
			", actionClass=" + actionClass +
			", actionMethod=" + actionMethod +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
//			", actionOutDesc=" + actionOutDesc +
			"}";
	}

	/**
	 * 获取实体标识(例如：com.sky.bluesky.model.TestRule  最后得到 testRule)
	 */
	public String getActionClazzIdentify() {
		int index = actionClass.lastIndexOf(".");
		if(index+1 == actionClass.length()){
			actionClass = actionClass.substring(0,index);
			index  = actionClass.lastIndexOf(".");
		}
		return actionClass.substring(index + 1).substring(0, 1).toLowerCase() +
				actionClass.substring(index + 1).substring(1);
	}


	/**
	 * 获取实体标识(例如：com.sky.bluesky.model.TestRule  最后得到 testRule)
	 */
	public String getActionClazzIdentify1() {
		int index = actionClass.lastIndexOf(".");
		return actionClass.substring(index + 1) ;
	}
}
