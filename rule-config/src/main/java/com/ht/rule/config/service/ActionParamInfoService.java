package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.ActionParamInfo;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 * 动作参数信息表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
public interface ActionParamInfoService extends BaseService<ActionParamInfo> {


    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据动作id获取动作参数信息
     * @param actionId 参数
     */
    List<ActionParamInfo> findRuleActionParamByActionId(final Long actionId);

    /**
     * 重写insertOrupdate 方法
     * @param info ActionParamInfo
     * @return
     */

    boolean  updateParamInfo(ActionParamInfo info);

}
