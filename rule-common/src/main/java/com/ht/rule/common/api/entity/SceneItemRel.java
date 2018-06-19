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
 * @since 2017-12-25
 */
@ApiModel
@TableName("rule_scene_item_rel")
@Data
public class SceneItemRel extends Model<SceneItemRel> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(required= true,value = "")
	private Long id;
    /**
     * 场景id
     */
	@TableField("scene_id")
	@ApiModelProperty(required= true,value = "场景id")
	private Long sceneId;
    /**
     * 变量id
     */
	@TableField("entity_item_id")
	@ApiModelProperty(required= true,value = "变量id")
	private Long entityItemId;
    /**
     * 排序
     */
	@ApiModelProperty(required= true,value = "排序")
	private Integer sort;
	@TableField("cre_time")
	@ApiModelProperty(required= true,value = "创建时间")
	private Date creTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SceneItemRel{" +
			"id=" + id +
			", sceneId=" + sceneId +
			", entityItemId=" + entityItemId +
			", sort=" + sort +
			", creTime=" + creTime +
			"}";
	}
}
