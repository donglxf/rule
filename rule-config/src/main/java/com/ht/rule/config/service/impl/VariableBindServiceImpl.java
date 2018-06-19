package com.ht.rule.config.service.impl;

import com.ht.rule.common.api.entity.VariableBind;
import com.ht.rule.common.api.mapper.VariableBindMapper;
import com.ht.rule.config.service.VariableBindService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-10
 */
@Service
public class VariableBindServiceImpl extends BaseServiceImpl<VariableBindMapper, VariableBind> implements VariableBindService {

    @Autowired
    private VariableBindMapper variableBindMapper;


    @Override
    public Integer myUpdate(String senceVersionId,String variableCode,String tmpValue,String bindTable,String bindColumn) {
        return variableBindMapper.myUpdate(senceVersionId,variableCode,tmpValue,bindTable,bindColumn);
    }

}
