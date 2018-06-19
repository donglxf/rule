package com.ht.rule.common.anno;

import java.lang.annotation.*;

/**
 * 删除查询是否有绑定
 * @author zhangp
 * @Date: 2018/3/2
 * @Time: 11:09
 * @annotation OperationLogger
 */
@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationDelete
{
    String idVal() default "";
    /**
     * 表名和字段的集合,以下划线来区分
     */
    String[] tableColumn() default {};
}