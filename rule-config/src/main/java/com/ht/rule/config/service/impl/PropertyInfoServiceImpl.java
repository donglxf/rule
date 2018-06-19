package com.ht.rule.config.service.impl;

import com.ht.rule.common.api.entity.PropertyInfo;
import com.ht.rule.common.api.mapper.PropertyInfoMapper;
import com.ht.rule.config.service.PropertyInfoService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 规则基础属性信息表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class PropertyInfoServiceImpl extends BaseServiceImpl<PropertyInfoMapper, PropertyInfo> implements PropertyInfoService {

}
