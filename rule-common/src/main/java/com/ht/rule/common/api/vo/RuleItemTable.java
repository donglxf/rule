package com.ht.rule.common.api.vo;

import java.io.Serializable;

/**
 * 说明：table显示的头部，变量
 *
 * @auther 张鹏
 * @create
 */
public class RuleItemTable implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long entityId;

    private Long itemId;

    private String entityName;

    private String itemName;

    private Long id;

    private Integer sort;

    private Long sceneId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }
}
