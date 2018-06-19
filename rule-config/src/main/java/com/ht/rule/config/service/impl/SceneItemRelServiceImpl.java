package com.ht.rule.config.service.impl;

import com.ht.rule.common.api.entity.SceneItemRel;
import com.ht.rule.common.api.mapper.SceneItemRelMapper;
import com.ht.rule.config.service.SceneItemRelService;
import com.ht.rule.common.api.vo.RuleItemTable;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-25
 */
@Service
public class SceneItemRelServiceImpl extends BaseServiceImpl<SceneItemRelMapper, SceneItemRel> implements SceneItemRelService {

    @Autowired
    private SceneItemRelMapper sceneItemRelMapper;


    @Override
    public List<RuleItemTable> findItemTables(Long sceneId) {

        return  this.sceneItemRelMapper.findItemTables(sceneId);
    }
}
