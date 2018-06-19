package com.ht.rule.config.model;

/**
 * 描述：
 * CLASSPATH: com.sky.BaseRuleEntityItemInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public class BaseRuleEntityItemInfo extends BaseModel {
    private Long itemId;//主键
    private Long entityId;//实体id
    private String itemName;//字段名称
    private String itemIdentify;//字段标识
    private String itemDesc;//属性描述
    private String entityDesc; //实体描述
    private String entityIdentify; //实体标识
    private String entityName; //实体名

    public String getEntityIdentify() {
        return entityIdentify;
    }

    public void setEntityIdentify(String entityIdentify) {
        this.entityIdentify = entityIdentify;
    }

    public String getEntityDesc() {

        return entityDesc;
    }

    public void setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc;
    }

    public String getEntityName() {

        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIdentify() {
        return itemIdentify;
    }

    public void setItemIdentify(String itemIdentify) {
        this.itemIdentify = itemIdentify;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
