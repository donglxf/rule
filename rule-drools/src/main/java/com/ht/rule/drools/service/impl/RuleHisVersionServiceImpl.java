package com.ht.rule.drools.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.RuleHisVersion;
import com.ht.rule.common.api.mapper.RuleHisVersionMapper;
import com.ht.rule.common.api.vo.RuleHisVersionVo;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.common.vo.model.drools.DroolsActionForm;
import com.ht.rule.common.vo.model.drools.RpcRuleHisVersionParamter;
import com.ht.rule.common.vo.model.drools.RuleStandardResult;
import com.ht.rule.drools.service.RuleHisVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<RuleHisVersionVo> getRuleValidationResultNew(Long senceVersionId,List<DroolsActionForm> actionForms ) {

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

            for (int i = 0; i < actionForms.size(); i++) {
                DroolsActionForm form = actionForms.get(i);

                String ruleName = form.getRuleName();
                if(vo.getRuleName().equals(ruleName)){
                    vo.setValidationResult("0");
                    vo.setResult(JSON.toJSONString( form.getResult()));
                }
            }
            return vo;
        }).collect(Collectors.toList());

        return  ruleHisVersionVos;
    }
}
