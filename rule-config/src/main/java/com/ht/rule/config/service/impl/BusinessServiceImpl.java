package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.Business;
import com.ht.rule.common.api.entity.EntityItemCodeView;
import com.ht.rule.common.api.mapper.BusinessMapper;
import com.ht.rule.config.service.BusinessService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
@Service
public class BusinessServiceImpl extends BaseServiceImpl<BusinessMapper, Business> implements BusinessService {
    @Override
    public boolean checkKey(String key,String other,String id) {
        Integer count = 0;
        if(id != null ){
            count = this.baseMapper.selectCount(new EntityWrapper<Business>()
                    .eq(Business.BUSINESS_TYPE_CODE, key).and().ne(Business.BUSINESS_TYPE_ID,id));
        }else{
            count = this.baseMapper.selectCount(new EntityWrapper<Business>()
                    .eq(Business.BUSINESS_TYPE_CODE, key));
        }

        count = count == null?0:count;
        return count > 0 ? true:false;
    }

    @Override
    @Cacheable(value = "getIdCodeMap",key = "'getIdCodeMap'")
    public Map<String, String> getIdCodeMap() {
        Map<String, String> map = new HashMap<>();
        List<Business> list = this.baseMapper.selectList(null);
        list.forEach(business -> {
            map.put(business.getBusinessTypeId(),business.getBusinessTypeCode());
        });
        
        return map;
    }

}
