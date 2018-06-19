package com.ht.rule.drools.service.impl;

import com.ht.rule.common.api.entity.DroolsLog;
import com.ht.rule.common.api.mapper.DroolsLogMapper;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.drools.service.DroolsLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 规则执行日志表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2018-06-19
 */
@Service
public class DroolsLogServiceImpl extends BaseServiceImpl<DroolsLogMapper, DroolsLog> implements DroolsLogService {

}
