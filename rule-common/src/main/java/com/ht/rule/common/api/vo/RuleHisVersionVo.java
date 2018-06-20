package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.RuleHisVersion;
import lombok.Data;

import java.util.List;
@Data
public class RuleHisVersionVo extends RuleHisVersion {

    public String validationResult; // 匹配结果  1-未匹配，0-匹配

    public String executeRule;

    public String variableName;
    public String variableValue;

    public String versionId; // 版本id
    public List<String> tRuleName; // 规则名称集合

}
