package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.VariableBind;
import com.ht.rule.common.service.BaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-10
 */
public interface VariableBindService extends BaseService<VariableBind> {

    public Integer myUpdate(String senceVersionId, String variableCode, String tmpValue, String bindTable, String bindColumn);
}
