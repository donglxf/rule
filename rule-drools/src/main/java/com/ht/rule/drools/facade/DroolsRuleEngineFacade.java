package com.ht.rule.drools.facade;

import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.common.vo.model.drools.*;

import java.util.List;

public interface DroolsRuleEngineFacade {


    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 规则引擎执行方法
     * @param ruleExecutionObject facr对象信息
     * @param scene 场景
     */
    RuleExecutionObject excute(RuleExecutionObject ruleExecutionObject, final SceneInfoVersion scene) throws Exception;

    /**
     * 获取场景版本信息
     * @param scene 场景code
     * @param version 版本号，如果么有，查最新
     * @param type 1 决策 2评分卡
     * @return
     */
    SceneInfoVersion getScenInfoVersion(String scene, String version, Integer type);

    /**
     * 将参数信息转化为Ob
     * @param paramter
     * @return
     */
    RuleExecutionObject change2RuleExcutionObject(DroolsParamter paramter, SceneInfoVersion sceneInfoVersion );

    /**
     * 添加日志
     * @param ruleStandardResult
     * @param paramter
     * @param sceneInfoVersion
     */
    void log4drools(RuleStandardResult ruleStandardResult, DroolsParamter paramter, SceneInfoVersion sceneInfoVersion, long executeTime);
}
