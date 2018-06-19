package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.SceneInfo;

import java.util.List;

/**
 * <p>
 * 规则引擎使用场景 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface SceneInfoMapper extends SuperMapper<SceneInfo> {
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则引擎场景集合
     * @param sceneInfo 参数
     */
    List<SceneInfo> findBaseRuleSceneInfiList(SceneInfo sceneInfo);

}
