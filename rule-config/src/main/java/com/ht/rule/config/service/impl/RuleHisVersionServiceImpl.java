package com.ht.rule.config.service.impl;

import com.ht.rule.common.vo.model.drools.RpcRuleHisVersionParamter;
import com.ht.rule.common.api.entity.RuleHisVersion;
import com.ht.rule.common.api.mapper.RuleHisVersionMapper;
import com.ht.rule.config.service.RuleHisVersionService;
import com.ht.rule.common.api.vo.RuleHisVersionVo;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangzhen
 * @since 2018-01-10
 */
@Service
public class RuleHisVersionServiceImpl extends BaseServiceImpl<RuleHisVersionMapper, RuleHisVersion> implements RuleHisVersionService {

    @Autowired
    private RuleHisVersionMapper ruleHisVersionMapper;

    @Override
    public List<RuleHisVersionVo> getRuleValidationResult(Map<String, Object> paramMap) {
        return ruleHisVersionMapper.getRuleValidationResult(paramMap);
    }

    @Override
    public List<Map<String, Object>> getRuleBatchValidationResult(Map<String, Object> paramMap) {
        return ruleHisVersionMapper.getRuleBatchValidationResult(paramMap);
    }

    @Override
    public List<RuleHisVersion> getHisVersionListByVidName(RpcRuleHisVersionParamter paramter) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("versionId", paramter.getVersionId());
        map.put("ruleNames",paramter.gettRuleName() );
        List<RuleHisVersion> rules = ruleHisVersionMapper.getHisVersionListByVidName(map);
        return rules;
    }
}
