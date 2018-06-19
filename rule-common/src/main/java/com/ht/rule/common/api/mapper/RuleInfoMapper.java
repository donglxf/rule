package com.ht.rule.common.api.mapper;

import com.ht.rule.common.api.entity.PropertyRel;
import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.PropertyInfo;
import com.ht.rule.common.api.entity.RuleInfo;
import com.ht.rule.common.api.entity.SceneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 规则信息 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface RuleInfoMapper extends SuperMapper<RuleInfo> {

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则信息集合
     * @param baseRuleInfo 参数
     */
    List<RuleInfo> findBaseRuleInfoList(RuleInfo baseRuleInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则属性信息
     * @param baseRulePropertyInfo 参数
     */
    List<PropertyInfo> findBaseRulePropertyInfoList(PropertyInfo baseRulePropertyInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleId 参数
     */
    List<PropertyRel> findRulePropertyListByRuleId(@Param("ruleId") Long ruleId);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param baseRuleSceneInfo 参数
     */
    List<RuleInfo> findBaseRuleListByScene(SceneInfo baseRuleSceneInfo);

    /**
     * 联合查询规则信息，包括了分组。多对一查询
     * @param sceneId
     * @return
     */
    List<RuleInfo> findListBySceneId(@Param("sceneId") Long sceneId);
}
