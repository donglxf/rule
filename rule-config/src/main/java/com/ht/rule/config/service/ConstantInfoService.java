package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.ConstantInfo;
import com.ht.rule.common.api.entity.EntityItemInfo;
import com.ht.rule.common.api.vo.EntitySelectVo;
import com.ht.rule.common.service.BaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-29
 */
public interface ConstantInfoService extends BaseService<ConstantInfo> {
    /**
     * 描述：设置变量的常量子集
     * @param * @param null
     * @return a
     * @autor 张鹏
     * @date 2018/1/16 14:57
     */
    EntitySelectVo setItemConstants(EntitySelectVo itemvo, EntityItemInfo item);
}
