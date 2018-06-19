package com.ht.rule.common.api.mapper;

import com.ht.rule.common.api.vo.ActionInfoVo;
import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.ActionInfo;
import com.ht.rule.common.api.entity.SceneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 规则动作定义信息 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionInfoMapper extends SuperMapper<ActionInfo> {
    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取动作列表
     *
     * @param baseRuleActionInfo 参数
     */
    List<ActionInfo> findBaseRuleActionInfoList(ActionInfo baseRuleActionInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    List<ActionInfo> findRuleActionListByScene(SceneInfo sceneInfo);



    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    List<ActionInfo> findRuleActionListByRule(@Param("ruleId") Long ruleId);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 查询是否有实现类的动作
     *
     * @param ruleId 规则id
     */
    Integer findRuleActionCountByRuleIdAndActionType(@Param("ruleId") Long ruleId);

    /**
     * 描述：通过id查询动作 且包含了参数信息
     * @param * @param null
     * @return a
     * @autor 张鹏
     * @date 2017/12/26 11:24
     */
    List<ActionInfoVo> findByIds(@Param("ids") String ids);

    /**
     * 查询所有动作
     * @return
     */
    List<ActionInfoVo> findActionAllVos(@Param("businessId") String businessId);

    boolean updateInfo(ActionInfo info);
}
