package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.RuleHisVersion;

import java.util.List;

public class RuleHisVersionVo extends RuleHisVersion {

    public String validationResult; // 匹配结果  1-未匹配，0-匹配

    public String executeRule;

    public String variableName;
    public String variableValue;

    public String versionId; // 版本id
    public List<String> tRuleName; // 规则名称集合

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public List<String> gettRuleName() {
        return tRuleName;
    }

    public void settRuleName(List<String> tRuleName) {
        this.tRuleName = tRuleName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getVariableName() {

        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getExecuteRule() {
        return executeRule;
    }

    public void setExecuteRule(String executeRule) {
        this.executeRule = executeRule;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
}
