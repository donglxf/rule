package com.ht.rule.common.vo.model.drools;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：规则全局对象
 * CLASSPATH: com.sky.RuleExecutionResult
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
@Data
public class RuleExecutionResult implements Serializable {

    //规则执行中需要保存的数据
    private Map<String,Object> map = new HashMap<>();

    // 全局统计结果
    private int total;

    private List<DroolsActionForm> defalutActions;

}
