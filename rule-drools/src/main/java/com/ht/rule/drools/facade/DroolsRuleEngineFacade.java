package com.ht.rule.drools.facade;

import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.common.vo.model.drools.*;

import java.util.List;
import java.util.Map;

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
    SceneInfoVersion getScenInfoVersion(String scene, String version, String businessCode, Integer type);

    /**
     * 将参数信息转化为Ob
     * @return
     */
    RuleExecutionObject change2RuleExcutionObject(Map<String, Object> data, SceneInfoVersion sceneInfoVersion );

    /**
     * 添加日志
     * @param actionForms 结果
     * @param sceneInfoVersion
     */
    void log4drools(List<DroolsActionForm> actionForms ,  Map<String,Object > data, SceneInfoVersion sceneInfoVersion, long executeTime);

    /**
     * 规则执行
     * @param sceneInfoVersion
     * @param data
     * @return
     */
    List<DroolsActionForm> excuteAll(SceneInfoVersion sceneInfoVersion, Map<String, Object> data,int type);
}
