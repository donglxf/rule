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
public class DroolsActionForm {
    private String ruleName;
    private List<String> result;
}
