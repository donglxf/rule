package com.ht.rule.common.api.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
public class RuleGradeFormVo {

    /**场景id*/
    private Long sceneId;
    /**
     * 实体类集合
     */
    private List<String> entityIds ;
    /**
     * 变量集合
     */
    private List<String> itemVals ;
    @NotNull
    @Valid
    private List<RuleSubmitVo> vos;


    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public List<String> getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }

    public List<String> getItemVals() {
        return itemVals;
    }

    public void setItemVals(List<String> itemVals) {
        this.itemVals = itemVals;
    }

    public List<RuleSubmitVo> getVos() {
        return vos;
    }

    public void setVos(List<RuleSubmitVo> vos) {
        this.vos = vos;
    }
}
