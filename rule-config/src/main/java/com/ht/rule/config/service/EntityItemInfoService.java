package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.EntityItemInfo;
import com.ht.rule.common.api.vo.RuleItemTable;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 实体属性信息 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface EntityItemInfoService extends BaseService<EntityItemInfo> {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     *
     * @param baseRuleEntityItemInfo 参数
     */
    List<EntityItemInfo> findBaseRuleEntityItemInfoList(EntityItemInfo baseRuleEntityItemInfo) throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据id获取对应的属性信息
     *
     * @param id 属性Id
     */
    EntityItemInfo findBaseRuleEntityItemInfoById(final Long id) throws Exception;

    /**
     * 描述： 通过变量获取 封装的变量 实体类值
     *
     * @param * @param itemId
     * @return a
     * @auhor 张鹏
     * @date 2017/12/27 12:30
     */
    RuleItemTable findRuleItemTableById(long itemId);


    List<EntityItemInfo> findEntityItemBySceneId(String sceneId);

    /**
     * 保存并清理缓存
     * @param itemInfo
     * @param businessId
     */
    void insertOrUpdate4clear(EntityItemInfo itemInfo, String businessId);
}
