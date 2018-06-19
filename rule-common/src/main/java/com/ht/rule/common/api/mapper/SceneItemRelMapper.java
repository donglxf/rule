package com.ht.rule.common.api.mapper;

import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.SceneItemRel;
import com.ht.rule.common.api.vo.RuleItemTable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-25
 */
public interface SceneItemRelMapper extends SuperMapper<SceneItemRel> {
    /**
     * 描述：通过场景获取相关表头信息集合
     *
     * @param * @param null
     * @return a
     * @autor 张鹏
     * @date 2017/12/25 11:25
     */
    List<RuleItemTable> findItemTables(Long sceneId);
}
