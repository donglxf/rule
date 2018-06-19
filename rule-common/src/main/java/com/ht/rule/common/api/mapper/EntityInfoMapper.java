package com.ht.rule.common.api.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.api.entity.EntityItemCodeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 规则引擎实体信息表 Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface EntityInfoMapper extends SuperMapper<EntityInfo> {


    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎实体信息
     *
     * @param baseRuleEntityInfo 参数
     */
    List<EntityInfo> findBaseRuleEntityInfoList(EntityInfo baseRuleEntityInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    EntityInfo findBaseRuleEntityInfoById(@Param("id") Long id);

    /**
     * 查询实体和
     * @param ids
     * @return
     */
    List<EntityInfo> findRuleEntityIn(@Param("ids") String ids);

    /**
     * 查询所有符合的对象
     * @return
     */
    List<EntityInfo> findRuleEntityAll(@Param("businessId") String businessId);

    List<EntityInfo> getListsByWhere(@Param("ew") Wrapper<EntityInfo> wrapper);
    /**
     * 获取id，key的建值对
     */
    List<EntityItemCodeView> getEntityItemIdCode(@Param("bussinessId") String bussinessId);
}
