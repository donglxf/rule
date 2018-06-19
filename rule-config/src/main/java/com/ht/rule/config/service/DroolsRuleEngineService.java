package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.common.vo.model.drools.RuleExecutionObject;

public interface DroolsRuleEngineService {
    /**
     *
     * @Title: getDroolsString
     * @Description: 获取规则字符串
     * @param @param scene 场景名
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public String getDroolsString(final Long sceneId) throws Exception ;

    /**
     *
     * @Title: getDroolsString
     * @Description: 根据id查找场景标识
     * @param @param scene 场景名
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public String getSceneIdentifyById(String id) throws Exception ;
}
