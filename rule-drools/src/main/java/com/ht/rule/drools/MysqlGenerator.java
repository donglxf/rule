package com.ht.rule.drools;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.ht.rule.common.generator.CodeCreateUtil;
import com.ht.rule.common.generator.InputConfig;


/**
 *code is far away from bug with the animal protecting
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 *
 *   @Description : MybatisPlus代码生成器
 *   ---------------------------------
 *   @Author : Liang.Guangqing
 *   @Date : Create in 2017/9/19 14:48　
 */
public class MysqlGenerator {


    public static void main(String[] args) {
        String dburl = "jdbc:mysql://172.16.200.111/dispatch?characterEncoding=utf8";
        InputConfig inputConfig = new InputConfig("com.ht.dispatch.",
                "com.ht.rule.common",
                "rule",
                "dispatch-rule-service",
                "dp_");
        DataSourceConfig dataSourceConfig = CodeCreateUtil.setDataSorceConfig("dispatch", "xGvZ09ZF", dburl);
        CodeCreateUtil.execute(inputConfig, dataSourceConfig, "rule_dp_user_select_log");

    }
}