package com.ht.rule.common.vo.model.drools;

import lombok.Data;

import java.util.List;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
@Data
public class DroolsTreadResult {
    private List<DroolsActionForm> list;
    private  RuleExecutionResult res;
    private  RuleExecutionObject object;
}
