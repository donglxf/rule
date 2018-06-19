package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.api.entity.SceneEntityRel;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 场景实体关联表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface SceneEntityRelService extends BaseService<SceneEntityRel> {


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景信息获取相关的实体信息
     * @param sceneInfo 参数
     */
    List<EntityInfo> findBaseRuleEntityListByScene(SceneInfo sceneInfo)throws Exception;

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询场景与实体关系表信息
     * @param baseRuleSceneEntityRelInfo 参数
     * @param page 分页参数
     */
    List<SceneEntityRel> findBaseRuleSceneEntityRelInfoList(SceneEntityRel baseRuleSceneEntityRelInfo, PageInfo page);


}
