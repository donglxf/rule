package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.ActionParamInfo;
import com.ht.rule.common.api.mapper.ActionParamInfoMapper;
import com.ht.rule.config.service.ActionParamInfoService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import com.ht.rule.common.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 动作参数信息表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class ActionParamInfoServiceImpl extends BaseServiceImpl<ActionParamInfoMapper, ActionParamInfo> implements ActionParamInfoService {

    protected static final Logger log = LoggerFactory.getLogger(ActionParamInfoServiceImpl.class);

    @Resource
    private ActionParamInfoMapper actionParamInfoMapper;


    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作id获取动作参数信息
     *
     * @param actionId 参数
     */
    @Override
    public List<ActionParamInfo> findRuleActionParamByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        return this.actionParamInfoMapper.findRuleActionParamByActionId(actionId);
    }

    @Override
    public boolean updateParamInfo(ActionParamInfo info) {
        try {
            if (ObjectUtils.isNotEmpty(info.getActionParamId())) {
                this.updateAllColumnById(info);
            } else {
                this.insert(info);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean checkKey(String key, String other, String id) {
        Integer count = 0;
        if (id != null) {
            count = this.baseMapper.selectCount(new EntityWrapper<ActionParamInfo>()
                    .eq("param_identify", key).and().ne("action_param_id", id));
        } else {
            count = this.baseMapper.selectCount(new EntityWrapper<ActionParamInfo>()
                    .eq("param_identify", key));
        }

        count = count == null ? 0 : count;
        return count > 0 ? true : false;
    }
}
