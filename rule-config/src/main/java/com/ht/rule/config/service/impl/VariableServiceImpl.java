package com.ht.rule.config.service.impl;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.Variable;
import com.ht.rule.common.api.mapper.VariableMapper;
import com.ht.rule.config.service.VariableService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 规则引擎常用变量 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class VariableServiceImpl extends BaseServiceImpl<VariableMapper, Variable> implements VariableService {


    @Resource
    private VariableMapper baseRuleVariableInfoMapper;

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据变量类型或数值类型获取变量集合信息
     *
     * @param baseRuleVariableInfo 参数
     * @param pageInfo             分页参数
     */
    @Override
    public List<Variable> findBaseRuleVariableInfoList(Variable baseRuleVariableInfo, PageInfo pageInfo) throws Exception {
        return this.baseRuleVariableInfoMapper.findBaseRuleVariableInfoList(baseRuleVariableInfo);
    }
}
