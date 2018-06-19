package com.ht.rule.common.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 场景实体关联表
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@ApiModel
@TableName("rule_scene_entity_rel")
@Data
public class SceneEntityRel extends Model<SceneEntityRel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("scene_entity_rel_id")
	@ApiModelProperty(required= true,value = "主键")
	private Long sceneEntityRelId;
    /**
     * 场景
     */
	@TableField("scene_id")
	@ApiModelProperty(required= true,value = "场景")
	private Long sceneId;
    /**
     * 实体
     */
	@TableField("entity_id")
	@ApiModelProperty(required= true,value = "实体")
	private Long entityId;



	@Override
	protected Serializable pkVal() {
		return this.sceneEntityRelId;
	}

	@Override
	public String toString() {
		return "SceneEntityRel{" +
			"sceneEntityRelId=" + sceneEntityRelId +
			", sceneId=" + sceneId +
			", entityId=" + entityId +
			"}";
	}
}
