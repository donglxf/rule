package com.ht.rule.config.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ActionInfo;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.api.vo.ActionInfoVo;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 规则动作定义信息 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionInfoService extends BaseService<ActionInfo> {
    /**
     * 评分卡动作id
     */
    public final long FEN_CARD_ACTION_ID = 3L;
    /**
     * 评分卡 分值 参数ID
     */
    public final long FEN_CARD_ACTION_PARAM_F_ID = 3L;
    /**
     * 评分卡 权值参数ID
     */
    public final long FEN_CARD_ACTION_PARAM_WEIGHT_ID = 6L;

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取动作列表
     *
     * @param baseRuleActionInfo 参数
     * @param page               分页信息
     */
    Page<ActionInfo> findBaseRuleActionInfoPage(ActionInfo baseRuleActionInfo, Page page) throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    List<ActionInfo> findRuleActionListByScene(SceneInfo sceneInfo) throws Exception;


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    List<ActionInfo> findRuleActionListByRule(final Long ruleId) throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 查询是否有实现类的动作
     *
     * @param ruleId 规则id
     */
    Integer findRuleActionCountByRuleIdAndActionType(final Long ruleId);

    /**
     * 描述：通过in的id 查询出相关的动作库集合，包括了参数集合
     * @param * @param ids
     * @auhor 张鹏
     * @date 2017/12/26 11:22
     */
    List<ActionInfoVo> findByIds(String ids);

    /**
     *
     * @param sceneId
     * @return
     */
    List<ActionInfoVo> findActionVos(Long sceneId);

    /**
     * 查询所有的动作
     * @return
     */
    List<ActionInfoVo> findActionAllVos(String businessId);

    /**
     * 重写insertOrupdate方法
     * @param actionInfo
     * @return
     */
    public boolean update(ActionInfo actionInfo);


    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取动作列表
     *
     * @param baseRuleActionInfo 参数
     * @param page               分页信息
     */
    PageInfo<ActionInfo> findBaseRuleActionInfoPage(ActionInfo baseRuleActionInfo, PageInfo page) throws Exception;


}
