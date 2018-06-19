package com.ht.rule.config.service;

import com.ht.rule.common.api.entity.SceneItemRel;
import com.ht.rule.common.api.vo.RuleItemTable;
import com.ht.rule.common.service.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-25
 */
public interface SceneItemRelService extends BaseService<SceneItemRel> {
    /**
     * 描述：通过场景id查询 列表的表头部相关信息
     *
     * @param * @param null
     * @return a
     * @autor 张鹏
     * @date 2017/12/25 11:23
     */
    List<RuleItemTable> findItemTables(Long scene_id);
}
