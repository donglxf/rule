package com.ht.rule.config.service;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张鹏
 * @since 2018-01-03
 */
public interface SceneVersionService extends BaseService<SceneVersion> {

    Page<SceneInfoVersion> selectVersionPage(Page<SceneInfoVersion> pages, Wrapper<SceneInfoVersion> wrapper);

    /**
     * 获取所有测试版本
     * @param pages
     * @param wrapper
     * @return
     */
    Page<SceneVersion> getNoBindVariableRecord(Page<SceneVersion> pages, Wrapper<SceneVersion> wrapper, Map<String, Object> paramMap);

    /**
     * 获取最大测试版本号
     * @param paramMap
     * @return
     */
     Map<String,Object> getMaxTestVersion(Map<String, Object> paramMap);

    /**
     * 通过策略唯一标示和版本查询策略版本信息
     * @param code 策略唯一标示
     * @param version 策略版本
     * @return
     */
     SceneVersion querySceneVersionInfoByCodeAndVersion(String code, String version);

    /**
     * 添加 版本信息的规则描述文件和使用过的对象，变量
     * @param sceneInfo
     * @param version
     */
     void addRuleDescAndVarids(SceneInfo sceneInfo, SceneVersion version);

     /**
     * 统计规则响应时间信息
     */
     Map<String,Object> staticRuleExecuteInfo(Map<String, Object> map);

     /**
     * 统计规则执行数
     */
     List<Map<String,Object>> staticRuleExecuteTotal(Map<String, Object> map);


    /**
     * 获取最新版本记录
     * @param parmaMap sceneIdentify：  type:
     * @return
     * @throws Exception
     */
    public SceneVersion getLastVersionByType(Map<String, Object> parmaMap) throws Exception;

    /**
     * 根据版本id获取当前版本记录
     * @param parmaMap
     * @return
     * @throws Exception
     */
    public SceneVersion getInfoByVersionId(Map<String, Object> parmaMap) throws Exception;


    /**
     * 查询当前模型使用版本
     * @param parmaMap
     * @return
     */
    public List<SceneVersion> getSenceVersion(Map<String, Object> parmaMap);
}
