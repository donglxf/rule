package com.ht.rule.config.facade;

/**
 * <p>
 *  服务类-验证key的唯一性
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
public interface CheckKeyFacade {
    /**
     * 验证key值的唯一性
     * @param key
     * @return
     */
    public boolean checkKey(String key, Integer type, String other, String id);
}
