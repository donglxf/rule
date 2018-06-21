package com.ht.rule.config.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.mapper.*;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.mapper.*;
import com.ht.rule.config.service.SceneVersionService;
import com.ht.rule.config.service.VariableBindService;
import com.ht.rule.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2018-01-03
 */
@Service
public class SceneVersionServiceImpl extends BaseServiceImpl<SceneVersionMapper, SceneVersion> implements SceneVersionService {

    @Autowired
    private SceneInfoVersionMapper sceneInfoVersionMapper;

    @Autowired
    private SceneVersionMapper sceneVersionMapper;
    @Autowired
    private EntityItemInfoMapper itemInfoMapper;

    @Autowired
    private RuleHisVersionMapper ruleHisVersionMapper;
    @Autowired
    private VariableBindMapper variableBindMapper;
    @Autowired
    private RuleInfoMapper infoMapper;
    @Autowired
    private VariableBindService variableBindService;

    @Override
    public Page<SceneInfoVersion> selectVersionPage(Page<SceneInfoVersion> pages, Wrapper<SceneInfoVersion> wrapper) {
        SqlHelper.fillWrapper(pages, wrapper);
        pages.setRecords(this.sceneInfoVersionMapper.selectPage(pages, wrapper));
        return pages;
    }

	@Override
	public Page<SceneVersion> getNoBindVariableRecord(Page<SceneVersion> pages, Wrapper<SceneVersion> wrapper, Map<String,Object> paramMap) {
		SqlHelper.fillWrapper(pages, wrapper);
        pages.setRecords(sceneVersionMapper.getNoBindVariableRecord(pages, paramMap));
		return pages;
	}
	
	@Override
	public Map<String,Object> getMaxTestVersion(Map<String,Object> paramMap) {
		return sceneVersionMapper.getMaxTestVersion(paramMap);
	}

	@Override
    public SceneVersion querySceneVersionInfoByCodeAndVersion(String code, String version){
        /*Map<String,Object> paramter = new HashMap<String,Object>();
        paramter.put("scene_identify",code);
        paramter.put("official_version",version);*/
        EntityWrapper<SceneVersion> wrapper = new EntityWrapper<>();
        SceneVersion sceneVersion = new SceneVersion();
        sceneVersion.setSceneIdentify(code);
        sceneVersion.setOfficialVersion(version);
        wrapper.setEntity(sceneVersion);
        wrapper.orderBy("cre_time",false);
        List<SceneVersion> sceneVersionList = sceneVersionMapper.selectList(wrapper);
        if(sceneVersionList != null && sceneVersionList.size() > 0){
            return sceneVersionList.get(0);
        }
        return null;
    }

    @Override
    public void addRuleDescAndVarids(SceneInfo sceneInfo, SceneVersion version) {
        //查询场景的所有规则集合
        Wrapper<RuleInfo> infoWrapper = new EntityWrapper<>();
        infoWrapper.eq("scene_id",sceneInfo.getSceneId());
        List<RuleInfo> rules = infoMapper.selectList(infoWrapper);
        //获取去除重复的 变量
        Map<String,String> bindMap = new HashMap<>();
        List<VariableBind> binds = new ArrayList<>();
        rules.forEach(rule ->{
            //添加规则描述信息
            RuleHisVersion ruleHisVersion = new RuleHisVersion();
            ruleHisVersion.setSenceVersionId(version.getVersionId());
            ruleHisVersion.setRuleName(rule.getRuleName());
            ruleHisVersion.setRuleDesc(rule.getRuleDesc());
            ruleHisVersion.setCreateTime(new Date());
            ruleHisVersion.setIsEffect("1");
            ruleHisVersionMapper.insert(ruleHisVersion);
            //添加规则变量，常量信息
            //查询所有使用过的变量，和对象
            List<EntityItemInfo> itemInfos = itemInfoMapper.selectItemBySceneId(sceneInfo.getSceneId());

            //操作新增操作
            itemInfos.forEach(itemInfo -> {
//                String code = itemInfo.getEntityInfo().getType().getValue()+"_"+itemInfo.getEntityInfo().getEntityIdentify()+"_"+itemInfo.getItemIdentify();
                String code = itemInfo.getEntityInfo().getEntityIdentify()+"_"+itemInfo.getItemIdentify();

                String name = itemInfo.getEntityInfo().getEntityName()+"_"+itemInfo.getItemName();
                String mapCode = bindMap.get(code);
                //判断Map里面是否有值
                if(StringUtils.isBlank(mapCode)){
                    bindMap.put(code,name);
                    VariableBind bind = new VariableBind();
                    bind.setIsEffect("1");
                    bind.setCreateTime(new Date());
                    bind.setSenceVersionId(version.getVersionId());
//                    bind.setVariableCode( itemInfo.getEntityInfo().getType().getValue()+"_"+itemInfo.getEntityInfo().getEntityIdentify()+"_"+itemInfo.getItemIdentify());
                    bind.setVariableCode( itemInfo.getEntityInfo().getEntityIdentify()+"_"+itemInfo.getItemIdentify());

                    bind.setVariableName(itemInfo.getEntityInfo().getEntityName()+"_"+itemInfo.getItemName());
                    bind.setDataType(itemInfo.getDataType().getValue().toString());
                    if(itemInfo.getConstantId() != null ){
                        bind.setConstantId(itemInfo.getConstantId());
                    }
                    binds.add(bind);
                }
            });
        });
        //批量插入变量
        variableBindService.insertBatch(binds);
    }

    @Override
    public Map<String, Object> staticRuleExecuteInfo(Map<String,Object> map) {
        List<Map<String,Object>> listMap=sceneVersionMapper.getRuleAgeTime(map);
        List<Map<String,Object>> ruleMap=new ArrayList<Map<String,Object>>();
        for (Map<String,Object> ma:listMap){
            Map<String,Object> paramMap=new HashMap<String,Object>();
            paramMap.put("versionId",ma.get("senceVersionId"));
            Map<String,Object> list=sceneVersionMapper.getRuleExecInfo(paramMap);
            if(list ==  null || list.size() == 0){
                continue;
            }
            ruleMap.add(list);
        }
        Map<String, Object> resultMap=new HashMap<String,Object>();
        resultMap.put("ruleMap",ruleMap);
        resultMap.put("listMap",listMap);
        return resultMap;
    }

    @Override
    public List<Map<String,Object>> staticRuleExecuteTotal(Map<String, Object> map) {
        List<Map<String,Object>> resultMap=sceneVersionMapper.getRuleExecTotal(map);
        return resultMap;
    }

    @Override
    public SceneVersion getLastVersionByType(Map<String,Object> parmaMap) throws Exception{
        return sceneVersionMapper.getLastVersionByType(parmaMap);
    }

    @Override
    public SceneVersion getInfoByVersionId(Map<String,Object> parmaMap) throws Exception{
        return sceneVersionMapper.getInfoByVersionId(parmaMap);
    }

    public List<SceneVersion> getSenceVersion(Map<String,Object> parmaMap){
        return sceneVersionMapper.getSenceVersion(parmaMap);
    }

}
