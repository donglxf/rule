package com.ht.rule.config.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.util.CalculateUtils;
import com.ht.rule.common.vo.model.drools.DroolsParamter;
import com.ht.rule.common.vo.model.drools.RpcRuleHisVersionParamter;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.config.service.*;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2018-01-03
 */
@RestController
@RequestMapping("/sceneVersion")
@Log4j2
public class SceneVersionController extends BaseController {

    @Autowired
    private RuleHisVersionService ruleHisVersionService;

    @Autowired
    private SceneVersionService sceneVersionService;

    @Autowired
    private SceneInfoService sceneInfoService;


    @Autowired
    private RuleActionVersionService ruleActionVersionService;
    @Autowired
    private ActionInfoService actionInfoService;

    @Autowired
    private DroolsRuleEngineService droolsRuleEngineService;


    @GetMapping("page")
    @ApiOperation(value = "分页查询")
    public PageResult<List<SceneInfoVersion>> page(String key, Integer sceneType, Long sceneId, Integer page, Integer limit) {
        Wrapper<SceneInfoVersion> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            wrapper.like("v.version", key);
            wrapper.or().like("title", key);
        }
        if (sceneType != null) {
            // wrapper.eq("scene_type",sceneType);
        }
        wrapper.andNew().eq("test_status", 0);
        wrapper.andNew().eq("type", 0);
        if (sceneId != null) {
            wrapper.andNew().eq("v.scene_id", sceneId);
        }
        wrapper.orderBy("v.scene_id");
        wrapper.orderBy("v.version", false);

        Page<SceneInfoVersion> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);
        pages = sceneVersionService.selectVersionPage(pages, wrapper);
        return PageResult.success(pages.getRecords(), pages.getTotal());
    }

    @GetMapping("page4zs")
    @ApiOperation(value = "分页查询")
    public PageResult<List<SceneInfoVersion>> page4zs(String key, Long sceneId, Integer page, Integer limit) {
        Wrapper<SceneInfoVersion> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            wrapper.like("v.version", key);
            wrapper.or().like("title", key);
        }
        //测试状态为 1 测试必须先都通过了
        wrapper.andNew().eq("test_status", 1);
        if (sceneId != null) {
            wrapper.andNew().eq("v.scene_id", sceneId);
        }
        wrapper.orderBy("test_status");
        wrapper.orderBy("v.version", false);

        Page<SceneInfoVersion> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);
        pages = sceneVersionService.selectVersionPage(pages, wrapper);
        return PageResult.success(pages.getRecords(), pages.getTotal());
    }

    @PostMapping("forbidden")
    @ApiOperation(value = "禁用")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<Integer> forbidden(Integer status, Long versionId) {

        if (versionId != null) {
            SceneVersion version = sceneVersionService.selectById(versionId);
            version.setStatus(status);
            sceneVersionService.updateById(version);
        } else {
            Result.success(-1);
        }
        return Result.success(0);
    }

    @PostMapping("push")
    @ApiOperation(value = "版本发布-测试版")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<Integer> push(SceneVersion version) throws Exception {

        SceneInfo sceneInfo = sceneInfoService.selectById(version.getSceneId());
        if (sceneInfo == null) {
            return Result.error(-1, "不存在");
        }
        String identify = sceneInfo.getSceneIdentify();
        //获取当前总共发布的数量
        Wrapper wrapperCount = new EntityWrapper();
        wrapperCount.eq("scene_id", sceneInfo.getSceneId());
        //wrapperCount.eq("type",0);
        int count = sceneVersionService.selectCount(wrapperCount);
        //获取最大版本号 以0.1 为单位递增

        double maxVersion = CalculateUtils.add(1.0, CalculateUtils.mul(count,0.1));;
        version.setVersion(maxVersion + "");
        //获取 规则字符串
        //String ruleDrlStr = droolsRuleRpc.getDroolsVersion(sceneInfo.getSceneId()+"");
        DroolsParamter paramter = new DroolsParamter();
        paramter.setSence(String.valueOf(sceneInfo.getSceneId()));
        log.info("规则生成id：====="+paramter.getSence());
       String droolsStr = droolsRuleEngineService.getDroolsString(Long.parseLong(paramter.getSence()));

       // Result result = droolsRuleRpc.obtainDroolsVersion(paramter);

        version.setSceneId(sceneInfo.getSceneId());
        version.setSceneIdentify(identify);
        version.setCreTime(new Date());
        version.setRuleDrl(droolsStr);
        //启用
        version.setStatus(1);
        version.setTestStatus(0);
        //测试版
        version.setType(0);
        version.setCreUserId(this.getUserId());
        sceneVersionService.insert(version);
        //保存 版本附带信息
        sceneVersionService.addRuleDescAndVarids(sceneInfo, version);

        //版本动作关联表
        List<ActionInfo> actionInfos = actionInfoService.findRuleActionListByScene(sceneInfo);


        actionInfos.forEach(actionInfo -> {
            RuleActionVersion rel = new RuleActionVersion();
            rel.setVersionId(version.getVersionId());
            rel.setActionClass(actionInfo.getActionClass());
            rel.setActionId(actionInfo.getActionId());
            ruleActionVersionService.insert(rel);
        });
        return Result.success(0);
    }

    @PostMapping("getHisVersionListByVidName")
    @ApiOperation(value = "根据版本id和规则名获取规则信息")
    public Result<List<RuleHisVersion>> getHisVersionListByVidName(@RequestBody RpcRuleHisVersionParamter paramter) {
        List<RuleHisVersion> listMap = ruleHisVersionService.getHisVersionListByVidName(paramter);
        return Result.success(listMap);
    }

    @PostMapping("push4zs")
    @ApiOperation(value = "版本发布-正式版")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result<Integer> push(Long versionId) throws Exception {

        SceneVersion version = sceneVersionService.selectById(versionId);
        if (version == null) {
            return Result.error(-1, "不存在");
        }

        if (version.getTestStatus() == null || version.getTestStatus() < 1) {
            return Result.error(-1, "验证未通过，不可发版");
        }
        if (version.getType() > 0) {
            return Result.error(-1, "验证未通过，重复发布版本");
        }
        SceneInfo sceneInfo = sceneInfoService.selectById(version.getSceneId());
        String identify = sceneInfo.getSceneIdentify();
        //获取当前总共发布的数量
        Wrapper wrapperCount = new EntityWrapper();
        wrapperCount.eq("scene_id", sceneInfo.getSceneId());
        //正式版
        wrapperCount.eq("type", 1);
        int count = sceneVersionService.selectCount(wrapperCount);
        //获取最大版本号 以0.1 为单位递增
        double maxVersion =  CalculateUtils.add(1.0, CalculateUtils.mul(count,0.1));
        version.setOfficialVersion(maxVersion + "");
        version.setType(1);
        sceneVersionService.updateById(version);

        return Result.success(0);
    }
}

