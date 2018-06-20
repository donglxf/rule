package com.ht.rule.config.service.impl;

import com.github.pagehelper.PageInfo;
import com.ht.rule.common.api.entity.ConditionInfo;
import com.ht.rule.common.api.mapper.ConditionInfoMapper;
import com.ht.rule.config.service.ConditionInfoService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.ussp.bean.LoginUserInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则条件信息表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class ConditionInfoServiceImpl extends BaseServiceImpl<ConditionInfoMapper, ConditionInfo> implements ConditionInfoService {

    @Autowired
    LoginUserInfoHelper userInfoHelper;
    @Resource
    private ConditionInfoMapper conditionInfoMapper;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     */
    @Override
    public List<ConditionInfo> findRuleConditionInfoByRuleId(Long ruleId) throws Exception {
        if(null == ruleId){
            throw new NullPointerException("参数缺失");
        }
        return this.conditionInfoMapper.findRuleConditionInfoByRuleId(ruleId,null);
    }

    @Override
    public ConditionInfo add(ConditionInfo conditionInfo, Long ruleId) {
        long creUid = 111;
        conditionInfo.setRuleId(ruleId);
        conditionInfo.setCreTime(new Date());
        conditionInfo.setCreUserId(StringUtils.isEmpty( userInfoHelper.getUserId()) ? "-1" :  userInfoHelper.getUserId());
        conditionInfo.setIsEffect(1);
        conditionInfo.setConditionName("-----");
        this.conditionInfoMapper.insert(conditionInfo);
        return conditionInfo;
    }

    @Override
    public PageInfo<ConditionInfo> findBaseRuleConditionInfoPage(ConditionInfo baseRuleConditionInfo, PageInfo page) throws Exception {
        return null;
    }

}
