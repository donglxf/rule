package com.ht.rule.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.config.service.RuleHisVersionService;
import com.ht.rule.config.service.SceneVersionService;
import com.ht.rule.common.api.vo.RuleHisVersionVo;
import com.ht.rule.common.api.vo.VariableBindVo;
import com.ht.rule.common.comenum.RuleTestStateEnum;
import com.ht.rule.common.comenum.VbindStateEnum;
import com.ht.rule.common.result.PageResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/strategy/")
public class StrategyController {
    @Autowired
    private SceneVersionService sceneVersionService;

    @Autowired
    private RuleHisVersionService ruleHisVersionService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询")
    public PageResult<List<SceneVersion>> rulePage(String key, String businessLine, String businessType, Integer page, Integer limit) {
        Map<String,Object> paramMap=new HashMap<String,Object>();
        Wrapper<SceneVersion> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(key)) {
//            wrapper.like("rv.title", key);
//            wrapper.or().like("rv.comment", key);
//            wrapper.or().like("rv.version", key);
            key = VbindStateEnum.findByName(key) != null ? VbindStateEnum.findByName(key).getCode() : RuleTestStateEnum.findByName(key)==null ? key : RuleTestStateEnum.findByName(key).getCode();
            paramMap.put("title",key);
            paramMap.put("is_bind_var", key);
            paramMap.put("test_status", key);
            paramMap.put("version",key);
        }
        if(StringUtils.isNotBlank(String.valueOf(businessLine))){
            paramMap.put("business_id",businessLine);
        }
        if(StringUtils.isNotBlank(businessType)){
            paramMap.put("scene_type",businessType);
        }
        Page<SceneVersion> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);

        pages = sceneVersionService.getNoBindVariableRecord(pages, wrapper,paramMap);
        return PageResult.success(pages.getRecords(), pages.getTotal());
    }

//    @GetMapping("manuaRuleMatchResult/{logId}/{versionId}")
    @GetMapping("manuaRuleMatchResult")
    @ApiOperation(value = "获取手动规则验证结果")
    public PageResult<List<RuleHisVersionVo>> manuaRuleMatchResult(@RequestParam(name = "logId") String logId, @RequestParam(name="versionId") String versionId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", logId);
        paramMap.put("senceVersionId", versionId);
        List<RuleHisVersionVo> list = ruleHisVersionService.getRuleValidationResult(paramMap);
        return PageResult.success(list, 0);
    }

//    @GetMapping("ruleMatchResult/{logId}/{versionId}")
    @GetMapping("ruleMatchResult")
    @ApiOperation(value = "获取规则验证结果")
    public PageResult<Map<String, Object>> ruleMatchResult(@RequestParam("logId") String logId, @RequestParam("versionId") String versionId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", logId);
        paramMap.put("senceVersionId", versionId);
        PageResult<List<RuleHisVersionVo>> list = manuaRuleMatchResult(logId, versionId);

        // 根据批次号查询传入变量名和值
        List<Map<String, Object>> listMap = ruleHisVersionService.getRuleBatchValidationResult(paramMap);
        List<RuleHisVersionVo> paramList = new ArrayList<RuleHisVersionVo>();
        for (Map<String, Object> ma : listMap) {
            JSONObject json = JSONObject.parseObject((String) ma.get("in_paramter")).getJSONObject("data");
            RuleHisVersionVo vo = new RuleHisVersionVo();
            vo.setVariableName((String) ma.get("variable_name"));
            vo.setVariableValue((String) json.get(ma.get("variable_code")));
            paramList.add(vo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ruleResult", list.getData());
        map.put("dataResult", paramList);
        return PageResult.success(map, 0);
    }


    @PostMapping("variablePass")
    @ApiOperation(value = "规则验证通过or不通过")
    public PageResult<Integer> variablePass(VariableBindVo entityInfo) {
        Wrapper<SceneVersion> wrapper = new EntityWrapper<>();
        wrapper.eq("version_id", entityInfo.getSenceVersionId());
        SceneVersion scene = sceneVersionService.selectOne(wrapper);
        scene.setTestStatus(Integer.parseInt(entityInfo.getTestState()));
        sceneVersionService.updateById(scene);
        return PageResult.success(0);
    }
}
