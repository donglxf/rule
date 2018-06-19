package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.Variable;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 规则引擎常用变量 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface VariableService extends BaseService<Variable> {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据变量类型或数值类型获取变量集合信息
     * @param baseRuleVariableInfo 参数
     * @param pageInfo 分页参数
     */
    List<Variable> findBaseRuleVariableInfoList(Variable baseRuleVariableInfo, PageInfo pageInfo) throws Exception;
}
