package com.ht.rule.drools.controller;

import com.alibaba.fastjson.JSON;
import com.ht.rule.common.api.entity.SceneInfo;
import com.ht.rule.common.api.entity.SceneInfoVersion;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.util.ObjectUtils;
import com.ht.rule.common.util.ThreadPoolUtils;
import com.ht.rule.common.vo.model.drools.*;
import com.ht.rule.drools.facade.DroolsRuleEngineFacade;
import com.ht.rule.drools.thread.ExcuteDroolsTask;
import com.ht.rule.drools.thread.ExcuteDroolsTask4param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

@RestController
@Log4j2
@Api(tags = "DroolsExcuteController", description = "规则引擎接口服务")
public class DroolsExcuteController {
    @Autowired
    private DroolsRuleEngineFacade droolsRuleEngineFacade;
    private final static String PARAM = "param_";

    private final static String DROOLSID = "droolsId";

    @PostMapping("/excuteDroolsScene")
    @ApiOperation(value = "通过决策版本号获取规则执行结果")
    public RuleExcuteResult excuteDroolsScene(@RequestBody DroolsParamter paramter) {
        RuleExcuteResult result = new RuleExcuteResult();
        SceneInfoVersion sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),1);
        if (ObjectUtils.isEmpty(sceneInfoVersion)) {
            result = new RuleExcuteResult(1, paramter.getSence() + "无可用正式版发布信息,请检查", null,null);
            return result;
        }
        List<DroolsActionForm> actionForms =  droolsRuleEngineFacade.excuteAll(sceneInfoVersion,paramter.getData(),Integer.parseInt(paramter.getType()));
        result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
        result.setCode(0);
        result.setData(actionForms);
        return result;
    }
    @PostMapping("/excuteDrools2changeParam")
    @ApiOperation(value = "参数适配")
    @Cacheable(value = "drools4param")
    public RuleExcuteResult4changeParam excuteDrools2changeParam(@RequestBody DroolsParamter4Param paramter) {
        //参数转化,不需要传入实体对象的前缀
        paramter.setDatas(paramter.getDatas().stream().map(data ->{
            Map<String,Object> da = new HashMap<>();
            data.keySet().forEach(key ->{
                Object v = data.get(key);
                if(key.indexOf(PARAM) != 0){
                    key = PARAM + key;
                }
                da.put(key,v);
            });
            da.put(DROOLSID,UUID.randomUUID());
            return da;
        }).collect(Collectors.toList()));
        SceneInfoVersion sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),1);
        if (ObjectUtils.isEmpty(sceneInfoVersion)) {
            return  RuleExcuteResult4changeParam.error(-1,paramter.getSence() + "无可用正式版发布信息,请检查");
        }
        //批量执行多线程
        ExcuteDroolsTask4param task = new ExcuteDroolsTask4param(droolsRuleEngineFacade,paramter.getDatas(),0,paramter.getDatas().size(),sceneInfoVersion,1);
        ForkJoinPool pool = ThreadPoolUtils.getForkJoinPool();
        ForkJoinTask<List<DroolsTreadResult>> submit = pool.submit(task);
        List<DroolsTreadResult> treadResults = new ArrayList<>();
        try {
            treadResults = submit.get();
            log.info("规则引擎执行完成.");
        } catch (InterruptedException e) {
            log.error("线程异常",e);
            return  RuleExcuteResult4changeParam.error(-1,paramter.getSence() + "执行异常");
        } catch (ExecutionException e) {
            log.error("线程异常",e);
            return  RuleExcuteResult4changeParam.error(-1,paramter.getSence() + "执行异常");
        }
        //进行数据装换
        List<Map<String,Object>> datas = change2resultMap(treadResults,paramter.getDatas());

        return new RuleExcuteResult4changeParam(0,"执行成功",datas,sceneInfoVersion.getVersionId().toString());
    }

    /**
     * 转化为所需要的结果
     * @param treadResults
     * @return
     */
    private List<Map<String,Object>> change2resultMap(List<DroolsTreadResult> treadResults,List<Map<String,Object>> oldDatas ) {
        List<Map<String,Object>> datas = new ArrayList<>();
        //遍历原始数据
        oldDatas.forEach(map -> {
            String droolsUid = map.get(DROOLSID).toString();
            treadResults.forEach(thread -> {
                Map<String, Object> param = (Map<String, Object>) thread.getObject().getFactObjectList().get(0);
                String droolsOldUid = param.get(DROOLSID).toString();
                //转化后的结果
                Map<String, Object> droolsOld = thread.getRes().getMap();
                //找到相同的，然后处理参数
                if (droolsUid.equals(droolsOldUid)) {
                    Map<String,Object> data = new HashMap<>();
                    map.keySet().forEach(key -> {
                        Object v = droolsOld.get(key);
                        if (v != null) {
                            data.put(key.substring(PARAM.length()), v);
                        }else if(!DROOLSID.equals(key)){
                            data.put(key.substring(PARAM.length()), map.get(key));
                        }
                    });
                    datas.add(data);
                    return;
                }
            });
        });
        return datas;
    }

    @PostMapping("/excuteDroolsGrade")
    @ApiOperation(value = "通过评分卡获取得分")
    public RuleExcuteResult excuteDroolsGrade(@RequestBody DroolsParamter paramter) {
        RuleExcuteResult result = new RuleExcuteResult();
        SceneInfoVersion sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),2);
        if (ObjectUtils.isEmpty(sceneInfoVersion)) {
            result = new RuleExcuteResult(1, paramter.getSence() + "无可用正式版发布信息,请检查", null,null);
            return result;
        }
        List<DroolsActionForm> actionForms =  droolsRuleEngineFacade.excuteAll(sceneInfoVersion,paramter.getData(),Integer.parseInt(paramter.getType()));
        result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
        result.setCode(0);
        result.setData(actionForms);
        double scope = actionForms.stream().map((form) ->  Double.parseDouble(form.getResult().get(0))).reduce((sum, cost) -> sum + cost).get();
        result.setGrade(scope);
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
    public Result<List<RuleExcuteResult>>  excuteDroolsScene4batch(@RequestBody DroolsParamter4Batch paramter) {
        List<RuleExcuteResult> results = new ArrayList<>();


        SceneInfoVersion sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),1);
        if (ObjectUtils.isEmpty(sceneInfoVersion)) {
           return  Result.error(-1,paramter.getSence() + "无可用正式版发布信息,请检查");
        }
        //批量执行多线程
        ExcuteDroolsTask task = new ExcuteDroolsTask(droolsRuleEngineFacade,paramter.getDatas(),0,paramter.getDatas().size(),sceneInfoVersion,Integer.parseInt(paramter.getType()) );
        ForkJoinPool pool = ThreadPoolUtils.getForkJoinPool();
        ForkJoinTask<List<List<DroolsActionForm>>> submit = pool.submit(task);
        List<List<DroolsActionForm>> actionformss = new ArrayList<>();
        try {
            actionformss = submit.get();
            log.info("规则引擎执行完成.");
        } catch (InterruptedException e) {
            log.error("线程异常",e);
            return  Result.error(-1,paramter.getSence() + "执行异常");
        } catch (ExecutionException e) {
            log.error("线程异常",e);
            return  Result.error(-1,paramter.getSence() + "执行异常");
        }

        actionformss.forEach(actions ->{
            RuleExcuteResult result = new RuleExcuteResult();
            if(CollectionUtils.isEmpty(actions)){
                result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
                result.setCode(-2);
                result.setData(null);
            }else{
                result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
                result.setCode(0);
                result.setData(actions);
            }
            results.add(result);
        });

        return Result.success(results);
    }


    @PostMapping("/excuteDroolsGrade4Batch")
    @ApiOperation(value = "通过评分卡批量获取得分")
    public Result<List<RuleExcuteResult>> excuteDroolsGrade(@RequestBody DroolsParamter4Batch paramter) {

        List<RuleExcuteResult> results = new ArrayList<>();

        SceneInfoVersion sceneInfoVersion = droolsRuleEngineFacade.getScenInfoVersion(paramter.getSence(),paramter.getVersion(),paramter.getBusinessCode(),2);
        if (ObjectUtils.isEmpty(sceneInfoVersion)) {
            return  Result.error(-1,paramter.getSence() + "无可用正式版发布信息,请检查");
        }
        //批量执行多线程
        ExcuteDroolsTask task = new ExcuteDroolsTask(droolsRuleEngineFacade,paramter.getDatas(),0,paramter.getDatas().size(),sceneInfoVersion,Integer.parseInt(paramter.getType()) );
        ForkJoinPool pool = ThreadPoolUtils.getForkJoinPool();
        ForkJoinTask<List<List<DroolsActionForm>>> submit = pool.submit(task);
        List<List<DroolsActionForm>> actionformss = new ArrayList<>();
        try {
            actionformss = submit.get();
            log.info("规则引擎执行完成.");
        } catch (InterruptedException e) {
            log.error("线程异常",e);
            return  Result.error(-1,paramter.getSence() + "执行异常");
        } catch (ExecutionException e) {
            log.error("线程异常",e);
            return  Result.error(-1,paramter.getSence() + "执行异常");
        }

        actionformss.forEach(actions ->{

            RuleExcuteResult result = new RuleExcuteResult();
            if(CollectionUtils.isEmpty(actions)){
                result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
                result.setCode(-2);
                result.setData(null);
            }else{
                result.setSenceVersoionId(sceneInfoVersion.getVersionId().toString());
                result.setCode(0);
                //jdk 8 合并运算
                double scope = actions.stream().map((form) ->  Double.parseDouble(form.getResult().get(0))).reduce((sum, cost) -> sum + cost).get();
                result.setGrade(scope);
                result.setData(actions);
            }
            results.add(result);
        });

        return Result.success(results);
    }


  /*  private RuleExcuteResult excute( DroolsParamter paramter,int type ){
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
    }*/
}