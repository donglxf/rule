package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 规则执行日志表，规则命中详细信息
 * </p>
 *
 * @author 张鹏
 * @since 2018-06-19
 */
@Data
@ApiModel
@TableName("rule_drools_detail_log")
public class DroolsDetailLog extends Model<DroolsDetailLog> {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(required= true,value = "")
	private Long id;
    /**
     * 日志id
     */
	@TableField("drools_logid")
	@ApiModelProperty(required= true,value = "日志id")
	private Long droolsLogid;
    /**
     * 命中的规则
     */
	@TableField("execute_rulename")
	@ApiModelProperty(required= true,value = "命中的规则")
	private String executeRulename;
    /**
     * 结果
     */
	@ApiModelProperty(required= true,value = "结果")
	private String result;
    /**
     * 规则号
     */
	@TableField("rule_num")
	@ApiModelProperty(required= true,value = "规则号")
	private String ruleNum;

	@TableField("is_hit")
	@ApiModelProperty(required= true,value = "是否命中")
	private int hit;


	public static final String ID = "id";

	public static final String DROOLS_LOGID = "drools_logid";

	public static final String EXECUTE_RULENAME = "execute_rulename";

	public static final String RESULT = "result";

	public static final String RULE_NUM = "rule_num";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
