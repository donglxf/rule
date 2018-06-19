package com.ht.rule.common.api.entity;

import java.io.Serializable;

public class HitRule implements Serializable{

    private Long senceVersionId;
    private String senceName;
    private String ruleName;
    private String ruleDesc;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getSenceVersionId() {
        return senceVersionId;
    }

    public void setSenceVersionId(Long senceVersionId) {
        this.senceVersionId = senceVersionId;
    }

    public String getSenceName() {
        return senceName;
    }

    public void setSenceName(String senceName) {
        this.senceName = senceName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }
}
