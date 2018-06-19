package com.ht.rule.config.facade;

import java.util.Map;

/**
 * <p>
 *  服务类-验证key的唯一性
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
public interface SceneRuleFacade {
    public Map<String,Object> getScene4Grade(Long sceneId) throws Exception;
}
