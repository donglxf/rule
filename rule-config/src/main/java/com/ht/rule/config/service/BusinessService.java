package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.Business;
import com.ht.rule.common.service.BaseService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
public interface BusinessService extends BaseService<Business> {
    Map<String,String> getIdCodeMap();
}
