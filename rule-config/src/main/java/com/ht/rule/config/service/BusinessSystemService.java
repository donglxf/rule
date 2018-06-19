package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.BusinessSystem;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 业务系统表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2018-04-08
 */
public interface BusinessSystemService extends BaseService<BusinessSystem> {
    /**
     * 描述：查询业务系统包含了业务线信息
     * @autor 张鹏
     * @date 2018/4/8 17:47
     */
    List<BusinessSystem> findSysBusinessList(String key);
}
