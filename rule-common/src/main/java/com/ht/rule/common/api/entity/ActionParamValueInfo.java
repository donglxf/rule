package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 动作参数对应的属性值信息表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_action_param_value_info")
@Data
public class ActionParamValueInfo extends Model<ActionParamValueInfo> {

    private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private String itemIds;
    /**
     * 主键
     */
    @TableId("action_param_value_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long actionParamValueId;
    @TableField(exist = false)
    private String clazz;
    /**
     * 动作规则关系主键
     */
	@TableField("rule_action_rel_id")
	@ApiModelProperty(required= true,value = "动作规则关系主键")
	private Long ruleActionRelId;
    /**
     * 动作参数
     */
	@TableField("action_param_id")
	@ApiModelProperty(required= true,value = "动作参数")
	@NotNull(message = "动作参数ID不能为空")
	private Long actionParamId;
    /**
     * 参数值
     */
	@TableField("param_value")
	@ApiModelProperty(required= true,value = "参数值")
	@NotNull(message = "动作参数值不能为空")
	@Length(min = 1, max = 200, message = "动作参数值长度超长了")
	//@Pattern(regexp="^[^ `~!@#^&*_|{}''\\\\[\\\\]<>_~@#……|{}]+$",message="动作参数值不能包含特殊字符")
	//@Pattern(regexp="^[^&#$<>~'\\\"\\\\|\\\\\\\\]*$",message="动作参数值不能包含特殊字符")
	//@Pattern(regexp="^[a-zA-Z0-9]+$",message="出生日期格式不正确")
	private String paramValue;
	/**
	 * 参数值
	 */
	@TableField("param_text")
	@ApiModelProperty(required= true,value = "参数值描述")
	private String paramText;
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
		return this.actionParamValueId;
	}

	@Override
	public String toString() {
		return "ActionParamValueInfo{" +
			"actionParamValueId=" + actionParamValueId +
			", ruleActionRelId=" + ruleActionRelId +
			", actionParamId=" + actionParamId +
			", paramValue=" + paramValue +
			", isEffect=" + isEffect +
			", creUserId=" + creUserId +
			", creTime=" + creTime +
			", remark=" + remark +
			"}";
	}
}
