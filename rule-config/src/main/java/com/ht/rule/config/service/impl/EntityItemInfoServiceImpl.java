package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.util.StringUtils;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.api.entity.EntityItemInfo;
import com.ht.rule.common.api.mapper.EntityInfoMapper;
import com.ht.rule.common.api.mapper.EntityItemInfoMapper;
import com.ht.rule.config.service.EntityItemInfoService;
import com.ht.rule.common.api.vo.RuleItemTable;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.common.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实体属性信息 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class EntityItemInfoServiceImpl extends BaseServiceImpl<EntityItemInfoMapper, EntityItemInfo> implements EntityItemInfoService {

    Logger logger = LoggerFactory.getLogger(EntityItemInfoServiceImpl.class);

    @Resource
    private EntityItemInfoMapper entityItemInfoMapper;

    @Resource
    private EntityInfoMapper entityMapper;

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     *
     * @param baseRuleEntityItemInfo 参数
     */
    @Override
    public List<EntityItemInfo> findBaseRuleEntityItemInfoList(EntityItemInfo baseRuleEntityItemInfo) throws Exception {
        return this.entityItemInfoMapper.findBaseRuleEntityItemInfoList(baseRuleEntityItemInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据id获取对应的属性信息
     *
     * @param id 属性Id
     */
    @Override
    public EntityItemInfo findBaseRuleEntityItemInfoById(Long id) throws Exception {
        if (null == id) {
            throw new NullPointerException("参数缺失");
        }
        return this.entityItemInfoMapper.findBaseRuleEntityItemInfoById(id);
    }

    @Override
    public RuleItemTable findRuleItemTableById(long itemId) {
        return this.entityItemInfoMapper.findRuleItemTableById(itemId);
    }

	@Override
	public List<EntityItemInfo> findEntityItemBySceneId(String sceneId) {
		List<Map<String,Object>> map = entityItemInfoMapper.findEntityItemBySceneId(sceneId);
		if(ObjectUtils.isNotEmpty(map)){
			List<Long> str=assemblyCondition(map,"conditionExpression");
			Wrapper<EntityItemInfo> wrapper = new EntityWrapper<>();
	        wrapper.in("item_id", str);
	        return entityItemInfoMapper.selectList(wrapper);
		}
		return null;
	}

    @Override
    @CacheEvict(value = "risk-rule",key = "'getEntityItemIdCode:'+#businessId")
    public void insertOrUpdate4clear(EntityItemInfo itemInfo, String businessId) {
        this.insertOrUpdate(itemInfo);
    }

    public List<Long> assemblyCondition(List<Map<String,Object>> map,String key){
		List<Long> list=new ArrayList<Long>();
		for (Map<String, Object> map2 : map) {
			String conditionExpression=(String) map2.get(key);
			list.add(Long.parseLong(conditionExpression.substring(1,conditionExpression.lastIndexOf("$"))));
		}
		return list;
	}

    /**
     * 验证此code在业务线中的唯一性
     * @param key
     * @param other
     * @param id
     * @return
     */
    @Override
    public boolean checkKey(String key,String other,String id) {
        Integer count = 0;
        List<EntityItemInfo> itemInfos = entityItemInfoMapper.selectList(new EntityWrapper<EntityItemInfo>()
                .eq("item_identify", key));
        if(itemInfos == null || itemInfos.size() < 1){
            return false;
        }
        //修改的情况
        if(StringUtils.isNotBlank(id)){
            count = this.baseMapper.selectCount(new EntityWrapper<EntityItemInfo>()
                    .eq("item_identify", key).and().ne("item_id",id));
        }else{
                //查询实体类
                EntityInfo entityInfo = entityMapper.selectById(other);
                count = entityItemInfoMapper.findCountInBusiness(key,entityInfo.getBusinessId(),id,entityInfo.getType().getCode());
        }
        count = count == null?0:count;
        return count > 0 ? true:false;
    }

}
