package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.BusinessSystem;
import com.ht.rule.common.api.mapper.BusinessSystemMapper;
import com.ht.rule.config.service.BusinessSystemService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务系统表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2018-04-08
 */
@Service
public class BusinessSystemServiceImpl extends BaseServiceImpl<BusinessSystemMapper, BusinessSystem> implements BusinessSystemService {
    @Autowired
    private BusinessSystemMapper businessSystemMapper;
    @Override
    public boolean checkKey(String key,String other,String id) {
        Integer count = 0;
        if(id != null ){
            count = this.baseMapper.selectCount(new EntityWrapper<BusinessSystem>()
                    .eq(BusinessSystem.BUSINESS_SYSTEM_CODE, key).and().ne(BusinessSystem.BUSINESS_SYSTEML_ID,id));
        }else{
            count = this.baseMapper.selectCount(new EntityWrapper<BusinessSystem>()
                    .eq(BusinessSystem.BUSINESS_SYSTEM_CODE, key));
        }

        count = count == null?0:count;
        return count > 0 ? true:false;
    }

    @Override
    public List<BusinessSystem> findSysBusinessList(String key) {
        return this.businessSystemMapper.findSysBusinessList(key);
    }
}
