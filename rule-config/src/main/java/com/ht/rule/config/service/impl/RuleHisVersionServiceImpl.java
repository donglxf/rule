package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.vo.model.drools.RpcRuleHisVersionParamter;
import com.ht.rule.common.api.entity.RuleHisVersion;
import com.ht.rule.common.api.mapper.RuleHisVersionMapper;
import com.ht.rule.config.service.RuleHisVersionService;
import com.ht.rule.common.api.vo.RuleHisVersionVo;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<RuleHisVersionVo> getRuleValidationResultNew(Long senceVersionId, List<String> ruleList) {

       List<RuleHisVersion> ruleHisVersions = ruleHisVersionMapper.selectList(
               new EntityWrapper<RuleHisVersion>().
                       eq("sence_version_id",senceVersionId));

        List<RuleHisVersionVo> ruleHisVersionVos = ruleHisVersions.stream().map(his->{
            RuleHisVersionVo vo = new RuleHisVersionVo();
            vo.setVersionId(his.getSenceVersionId().toString());
            vo.setExecuteRule(his.getRuleDesc());
            vo.setVariableName(his.getRuleName());
            vo.setValidationResult("1");
            vo.setRuleName(his.getRuleName());
            vo.setRuleDesc(his.getRuleDesc());
            ruleList.forEach(ruleName ->{
                if(vo.getRuleName().equals(ruleName))
                    vo.setValidationResult("0");
            });
            return vo;
        }).collect(Collectors.toList());

        return  ruleHisVersionVos;
    }
}
