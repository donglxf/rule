package com.ht.rule.common.api.vo;


import com.ht.rule.common.vo.model.drools.RpcHitRuleInfo;

import java.io.Serializable;

public class HitRuleInfoVo implements Serializable{

    public HitRuleInfoVo(){
        super();
    }

    public HitRuleInfoVo(RpcHitRuleInfo rpcHitRuleInfo){
        this.ruleName = rpcHitRuleInfo.getRuleName();
        this.ruleDesc = rpcHitRuleInfo.getRuleDesc();
        this.count = rpcHitRuleInfo.getCount();
    }


    private String ruleName;
    private String ruleDesc;
    private int count;

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
