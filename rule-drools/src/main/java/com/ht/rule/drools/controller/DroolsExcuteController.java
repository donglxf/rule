package com.ht.rule.drools.controller;

import com.alibaba.fastjson.JSON;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.util.ObjectUtils;
import com.ht.rule.common.vo.model.drools.*;
import com.ht.rule.drools.facade.DroolsRuleEngineFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Log4j2
@Api(tags = "DroolsExcuteController", description = "规则引擎接口服务")
public class DroolsExcuteController {
    @Autowired
    private DroolsRuleEngineFacade droolsRuleEngineFacade;


    @PostMapping("/excuteDroolsScene")
    @ApiOperation(value = "通过决策版本号获取规则执行结果")
    public RuleExcuteResult excuteDroolsScene(@RequestBody DroolsParamter paramter) {
        return excute(paramter,1);
    }

    @PostMapping("/excuteDroolsGrade")
    @ApiOperation(value = "通过评分卡获取得分")
    public RuleExcuteResult excuteDroolsGrade(@RequestBody DroolsParamter paramter) {
        RuleExcuteResult result =  excute(paramter,2);
        return result;
    }

/*    @PostMapping("/excuteDrools")
    @ApiOperation(value = "通过决策版本号获取规则执行结果")
    public RuleExcuteResult excuteDrools(@RequestBody DroolsForm form) {
        DroolsParamter paramter = new DroolsParamter();
        BeanUtils.copyProperties(form,paramter);
        List<DroolsForm.ItemValForm> list = form.getData();
        return null;
    }*/


    private RuleStandardResult getRuleStandardResult(List<DroolsActionForm> actionForms) {
        RuleStandardResult ruleStandardResult = new RuleStandardResult();
        List<String > rulelist = new ArrayList<>();
        List<String > results = new ArrayList<>();
        actionForms.forEach(form ->{
            results.add(JSON.toJSONString( form.getResult() ));
            rulelist.add(form.getRuleName());
        });
        ruleStandardResult.setResult(results);
        ruleStandardResult.setRuleList(rulelist);
        return ruleStandardResult;
    }


    @PostMapping("/excuteDroolsScene4batch")
    @ApiOperation(value = "通过决策版本号批量获取规则执行结果")
    public List<RuleExcuteResult> excuteDroolsScene4batch(@RequestBody DroolsParamter4Batch paramter) {

        return null;
    }


    @PostMapping("/excuteDroolsGrade4Batch")
    @ApiOperation(value = "通过评分卡批量获取得分")
    public Result<Double> excuteDroolsGrade(@RequestBody DroolsParamter4Batch paramter) {

        return null;
    }


    private RuleExcuteResult excute( DroolsParamter paramter,int type ){
        RuleExcuteResult data = new RuleExcuteResult();
        Long startTime = System.currentTimeMillis();
        SceneInfoVersion sceneInfoVersion = null;
        //执行结果
        List<DroolsActionForm> actionForms = null;
        // 业务数据转化
        try {
            sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),type);
            if (ObjectUtils.isEmpty(sceneInfoVersion)) {
                data = new RuleExcuteResult(1, paramter.getSence() + "无可用正式版发布信息,请检查", null,null);
                return data;
            }
            //封装数据为规则引擎可以使用的
            RuleExecutionObject object = droolsRuleEngineFacade.change2RuleExcutionObject(paramter,sceneInfoVersion);
            //执行规则引擎
            object = this.droolsRuleEngineFacade.excute(object,sceneInfoVersion);
            RuleExecutionResult res = (RuleExecutionResult) object.getGlobalMap().get("_result");
            if(type == 2)
                data.setGrade((double )res.getMap().get("scope"));
            //添加日志
            actionForms =  res.getDefalutActions();
            Long endTime = System.currentTimeMillis();
            Long executeTime = endTime - startTime;
            log.info("规则验证执行时间》》》》》" + String.valueOf(executeTime));
            // ruleStandardResult = getRuleStandardResult(actionForms);
            // 记录日志
            //if("1".equals(paramter.getType())){
            final List<DroolsActionForm> actionForms4log = actionForms;
            final SceneInfoVersion infoVersion = sceneInfoVersion;
            new Thread( () -> droolsRuleEngineFacade.log4drools(actionForms4log,paramter,infoVersion,executeTime) ).start();
            //  }
        } catch (Exception e) {
            log.info("规则引擎执行异常",e);
            data = new RuleExcuteResult(1, "执行异常", null,null);
        }
        data.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
        data.setCode(0);
        data.setData(actionForms);

        return data;
    }
}