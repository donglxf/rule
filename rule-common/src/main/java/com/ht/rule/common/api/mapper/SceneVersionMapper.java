package com.ht.rule.common.api.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.mapper.SuperMapper;
import com.ht.rule.common.api.entity.SceneVersion;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张鹏
 * @since 2018-01-03
 */
public interface SceneVersionMapper extends SuperMapper<SceneVersion> {
	/**
	 * 
	* @Title: getNoBindVariableRecord
	* @Description: 查询所有未绑定变量的版本记录
	* @param @return    设定文件
	* @return List<SceneVersion>    返回类型
	* @throws
	 */
	public List<SceneVersion> getNoBindVariableRecord(Page<SceneVersion> pages, Map<String, Object> paramMap);
//	public List<SceneVersion> getNoBindVariableRecord(Page<SceneVersion> pages, Wrapper<SceneVersion> wrapper);

	/**
	 *
	* @Title: getMaxTestVersion
	* @Description: 查找最大测试版本号
	* @param @param scene
	* @param @return    设定文件
	* @return float    返回类型
	* @throws
	 */
	public Map<String,Object> getMaxTestVersion(Map<String, Object> paramMap);

	/**
	 * 获取规则执行信息
	 * @return
	 */
	Map<String,Object> getRuleExecInfo(Map<String, Object> obj);

	/**
	 * 计算规则平均耗时
	 * @return
	 */
	List<Map<String,Object>> getRuleAgeTime(Map<String, Object> obj);

	/**
	 * 统计规则在某段时间内执行次数
	 * @return
	 */
	List<Map<String,Object>> getRuleExecTotal(Map<String, Object> obj);



	/**
	 * 获取最后版本，可以是测试或正式
	 * @Title: getTestLastVersion
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param parmaMap
	 * @param @return    设定文件
	 * @return RuleSceneVersion    返回类型
	 * @throws
	 */
	public SceneVersion getLastVersionByType(Map<String, Object> parmaMap);

	/**
	 * 根据版本号过去当前规则信息
	 * @param parmaMap
	 * @return
	 */
	public SceneVersion getInfoByVersionId(Map<String, Object> parmaMap);

	public List<SceneVersion> getSenceVersion(Map<String, Object> parmaMap);

}
