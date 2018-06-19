package com.ht.rule.common.vo.model.drools;

import java.io.Serializable;

public class RpcHitRuleInfo implements Serializable {

    private String senceName;
    private Long senceVersionId;
    private String ruleName;
    private String ruleDesc;
    private int count;


    public String getSenceName() {
        return senceName;
    }

    public void setSenceName(String senceName) {
        this.senceName = senceName;
    }

    public Long getSenceVersionId() {
        return senceVersionId;
    }

    public void setSenceVersionId(Long senceVersionId) {
        this.senceVersionId = senceVersionId;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


