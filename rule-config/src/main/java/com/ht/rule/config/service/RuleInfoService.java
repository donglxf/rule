package com.ht.rule.config.service;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.PropertyRel;
import com.ht.rule.common.service.BaseService;
import com.ht.rule.common.api.entity.PropertyInfo;
import com.ht.rule.common.api.entity.RuleInfo;
import com.ht.rule.common.api.entity.SceneInfo;

import java.util.List;

/**
 * <p>
 * 规则信息 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface RuleInfoService extends BaseService<RuleInfo> {


    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则属性信息
     * @param baseRulePropertyInfo 参数
     */
    List<PropertyInfo> findBaseRulePropertyInfoList(PropertyInfo baseRulePropertyInfo) throws Exception;

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleId 参数
     */
    List<PropertyRel> findRulePropertyListByRuleId(final Long ruleId)throws Exception;



    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param baseRuleSceneInfo 参数
     */
    List<RuleInfo> findBaseRuleListByScene(SceneInfo baseRuleSceneInfo)throws Exception;


    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则集合信息
     * @param baseRuleInfo 参数
     * @param page 分页参数
     */
    PageInfo<RuleInfo> findBaseRuleInfoPage(RuleInfo baseRuleInfo, PageInfo page) throws Exception;



    void clearBySceneId(Long sceneId);

    /**
     * 添加规则
     * @param scene
     */
    RuleInfo addRule(SceneInfo scene, int j);

    /**
     * 描述：联合查询，包括了分组信息
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2018/1/16 20:27
     */
    List<RuleInfo> findListBySceneId(Long sceneId);



}
