package com.ht.rule.common.vo.model.drools;

import java.util.List;

public class RpcRuleHisVersionParamter {

    private static final long serialVersionUID = 1L;

    public Long versionId; // 版本id
    public List<String> tRuleName; // 规则名称集合

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public List<String> gettRuleName() {
        return tRuleName;
    }

    public void settRuleName(List<String> tRuleName) {
        this.tRuleName = tRuleName;
    }


}
