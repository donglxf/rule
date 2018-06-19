package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.ActionParamValueInfo;
import com.ht.rule.common.api.entity.ConditionInfo;
import com.ht.rule.common.api.entity.EntityInfo;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
@ApiModel
public class RuleListVo {
    /**场景id*/
    private String sceneId;



    /**
     * 条件集合
     */
    private List<ConditionInfo> conditionInfos;
    /**
     * 动作集合
     */
    private List<ActionParamValueInfo> actionInfos;
    /**
     *导入的实体类
     */
    private List<EntityInfo> entityInfos ;

   // private List<>




}
