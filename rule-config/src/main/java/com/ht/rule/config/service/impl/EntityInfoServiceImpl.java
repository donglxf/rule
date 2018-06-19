package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.api.entity.EntityItemCodeView;
import com.ht.rule.common.api.mapper.EntityInfoMapper;
import com.ht.rule.common.api.mapper.SceneEntityRelMapper;
import com.ht.rule.config.service.EntityInfoService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则引擎实体信息表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class EntityInfoServiceImpl extends BaseServiceImpl<EntityInfoMapper, EntityInfo> implements EntityInfoService {


    @Autowired
    private EntityInfoMapper entityInfoMapper;

    @Autowired
    private SceneEntityRelMapper sceneEntityRelMapper;



    @Override
    public List<EntityInfo> findRuleEntityBySceneId(Long sceneId) {

        if (null == sceneId) {
            throw new NullPointerException("参数缺失");
        }

        String entityIds = sceneEntityRelMapper.findEntityIdsBySceneId(sceneId);

        if(StringUtils.isBlank(entityIds)){
            return new ArrayList<>();
        }
        return this.entityInfoMapper.findRuleEntityIn(entityIds);
    }

    @Override
    public List<EntityInfo> findRuleEntityAll(String businessId) {
      return  this.entityInfoMapper.findRuleEntityAll(businessId);
    }

    @Override
    public List<EntityInfo> getListsByWhere(Wrapper<EntityInfo> wrapper) {
        return this.entityInfoMapper.getListsByWhere(wrapper);
    }

    @Override
    public boolean checkKey(String key,String other,String id) {
        Integer count = 0;
        if(id != null ){
            count = this.baseMapper.selectCount(new EntityWrapper<EntityInfo>()
                    .eq("entity_identify", key).and().ne("entity_id",id));
        }else{
            count = this.baseMapper.selectCount(new EntityWrapper<EntityInfo>()
                    .eq("entity_identify", key));
        }
        count = count == null?0:count;

        return count > 0 ? true:false;
    }

    @Override
    @Cacheable(value = "risk-rule",key = "'getEntityItemIdCode:'+#bussinessId")
    public Map<String, Long> getEntityItemIdCode(String bussinessId) {

        List<EntityItemCodeView> list = entityInfoMapper.getEntityItemIdCode(bussinessId);
        Map<String,Long> data = new HashMap<>();
        if(!bussinessId.equals(commonBussinessId)){
            data = getEntityItemIdCode(commonBussinessId);
        }

        for (EntityItemCodeView entityItemCodeView : list) {
            if(entityItemCodeView != null )
            data.put(entityItemCodeView.getCode(),entityItemCodeView.getId());
        }
        return data;
    }

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎实体信息
     *
     * @param baseRuleEntityInfo 参数
     */
    @Override
    public List<EntityInfo> findBaseRuleEntityInfoList(EntityInfo baseRuleEntityInfo) throws Exception {
        return this.entityInfoMapper.findBaseRuleEntityInfoList(baseRuleEntityInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    @Override
    public EntityInfo findBaseRuleEntityInfoById(Long id) throws Exception {
        if (null == id) {
            throw new NullPointerException("参数缺失");
        }
        return this.entityInfoMapper.findBaseRuleEntityInfoById(id);
    }


}
