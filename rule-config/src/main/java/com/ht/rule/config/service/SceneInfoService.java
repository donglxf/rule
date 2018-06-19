package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则引擎使用场景 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface SceneInfoService extends BaseService<SceneInfo>{

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则引擎场景集合
     * @param sceneInfo 参数
     */
    List<SceneInfo> findBaseRuleSceneInfiList(SceneInfo sceneInfo) throws Exception;

    Result<Map<String,Object>> getGradeRules(Long sceneId);
}
