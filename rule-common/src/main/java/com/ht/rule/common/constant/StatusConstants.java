package com.ht.rule.common.constant;

/**
 * @Author Huang.zengmeng
 * @Description
 * @Date 2018/1/18 11:54
 */
public interface StatusConstants {
    /**
     * 对应数据库值为0
     */
    static final String NOT_YET_VALIDATE = "待验证";
    /**
     * 对应数据库值为1
     */
    static final String ALREADY_VALIDATE = "通过";
    /**
     * 对应数据库值为2
     */
    static final String NOT_ALLOW_VALIDATE = "未通过";


    /**
     * 对应数据库值为0
     */
    static final String NOT_YET_APPROVE = "待审核";
    /**
     * 对应数据库值为1
     */
    static final String ALREADY_APPROVED = "审核通过";
    /**
     * 对应数据库值为2
     */
    static final String NOT_ALLOW_ARRPOVED = "审核不通过";

    /**
     * 测试版
     */
    static final String BETA_VERSION = "测试版";

    /**
     * 正式版
     */
    static final String RELEASE_VERSION = "正式版";

    /**
     * 有效
     */
    static final String EFFECT = "启用";

    /**
     * 无效
     */
    static final String NOT_EFFECT = "禁用";

    /**
     * 未发布
     */
    static final String NOT_YET_PUBLISH = "未发布";

}
