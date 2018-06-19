package com.ht.rule.config.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则引擎实体信息表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface EntityInfoService extends BaseService<EntityInfo> {
    final static String  commonBussinessId = "5da5e2c7e47844c89d6cd3a910c89eb4";
    /**
     * 描述：通过业务线获取id和key的建值对
     * @autor 张鹏
     * @date 2018/4/17 19:58
     */
    Map<String, Long> getEntityItemIdCode(String bussinessId);
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎实体信息
     *
     * @param baseRuleEntityInfo 参数
     */
    List<EntityInfo> findBaseRuleEntityInfoList(EntityInfo baseRuleEntityInfo) throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    EntityInfo findBaseRuleEntityInfoById(final Long id) throws Exception;

    /**
     * 描述：查询实体类 和变量集合赋值
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2018/1/12 16:59
     */
    List<EntityInfo> findRuleEntityBySceneId(Long sceneId);

    /**
     * 描述：查询实体类 和变量集合赋值
     * @param * @param null
     * @return a
     * @auhor 张鹏
     * @date 2018/1/12 16:59
     */
    List<EntityInfo> findRuleEntityAll(String businessId);

    /**
     * 描述：通过条件查询实体对象
     * @auhor 张鹏
     * @date 2018/4/10 9:28
     */
    List<EntityInfo> getListsByWhere(Wrapper<EntityInfo> wrapper);
}

