package com.ht.rule.drools.service.impl;

import com.ht.rule.common.api.entity.DroolsDetailLog;
import com.ht.rule.common.api.mapper.DroolsDetailLogMapper;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.drools.service.DroolsDetailLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 规则执行日志表，规则命中详细信息 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2018-06-19
 */
@Service
public class DroolsDetailLogServiceImpl extends BaseServiceImpl<DroolsDetailLogMapper, DroolsDetailLog> implements DroolsDetailLogService {

}
