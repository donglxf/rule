package com.ht.rule.common.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 规则动作定义信息
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
//@TableName("SELECT i.item_id as id, CONCAT(e.type,'_',e.entity_identify,'_',i.item_identify) as code from `rule_entity_info` e LEFT JOIN rule_entity_item_info i on i.entity_id = e.entity_id ")
@Data
public class EntityItemCodeView  {

    private static final long serialVersionUID = 1L;

	private Long id;

	private String code;



}
