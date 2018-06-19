package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.BusinessSystem;

import java.util.List;

/**
 * <p>
 * 业务系统表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2018-04-08
 */
public interface BusinessSystemMapper extends SuperMapper<BusinessSystem> {
    /**
     * 描述：查询业务系统和其子集业务线集合
     * @param * @param key
     * @return a
     * @autor 张鹏
     * @date 2018/4/8 17:49
     */
    List<BusinessSystem> findSysBusinessList(String key);
}
