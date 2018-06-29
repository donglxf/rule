package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.api.mapper.SceneInfoMapper;
import com.ht.rule.config.service.SceneInfoService;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则引擎使用场景 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@Service
public class SceneInfoServiceImpl extends BaseServiceImpl<SceneInfoMapper, SceneInfo> implements SceneInfoService {

    @Resource
    private SceneInfoMapper sceneInfoMapper;
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎场景集合
     *
     * @param sceneInfo 参数
     */
    @Override
    public List<SceneInfo> findBaseRuleSceneInfiList(SceneInfo sceneInfo) throws Exception {
        return this.sceneInfoMapper.findBaseRuleSceneInfiList(sceneInfo);
    }

    @Override
    public Result<Map<String, Object>> getGradeRules(Long sceneId) {
        return null;
    }

    @Override
    public boolean checkKey(String key,String other,String id) {
        String business_id = other.split("_")[0];
        String type =  other.split("_")[1];
        List<SceneInfo> sceneInfos = sceneInfoMapper.selectList(new EntityWrapper<SceneInfo>()
        .eq("scene_identify",key)
        .eq("business_id",business_id)
        .eq("scene_type",type));

        if(sceneInfos.isEmpty()){
            return false;
        }else{
            //如果为修改
            SceneInfo sceneInfo = sceneInfos.get(0);

            if(StringUtils.isNotBlank(id) && sceneInfo.getSceneId().toString().equals(id)){
                return false;
            }
            return true;
        }
    }

}
