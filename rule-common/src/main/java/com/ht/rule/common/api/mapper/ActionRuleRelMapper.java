package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.ActionRuleRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 动作与规则信息关系表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionRuleRelMapper extends SuperMapper<ActionRuleRel> {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则与动作关系集合信息
     * @param baseRuleActionRuleRelInfo 参数
     */
    List<ActionRuleRel> findBaseRuleActionRuleRelInfoList(ActionRuleRel baseRuleActionRuleRelInfo);

    /**
     * 描述：通过ruleiD查询 出中间表和动作集合
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2017/12/25 17:51
     */
    List<ActionRuleRel> findActions(@Param("ruleId") Long ruleId);

    /**
     * 通过 场景查询所有关联的动作id集合
     * @param sceneId
     * @return
     */
    String findActionIdsBySceneId(@Param("sceneId") Long sceneId);
}
