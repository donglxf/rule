package com.ht.rule.config.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ht.rule.common.api.entity.ConstantInfo;
import com.ht.rule.common.api.entity.SceneVersion;
import com.ht.rule.common.api.entity.VariableBind;
import com.ht.rule.common.api.vo.ModelSence;
import com.ht.rule.common.result.Result;
import com.ht.rule.config.service.ConstantInfoService;
import com.ht.rule.config.service.SceneVersionService;
import com.ht.rule.config.service.VariableBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzm
 * @since 2018-01-17
 */
@RestController
@RequestMapping("/actProcRelease")
@Api(tags = "actProcReleaseController", description = "模型查询", hidden = true)
public class ActProcReleaseController {

    private static Logger logger = LoggerFactory.getLogger(ActProcReleaseController.class);

    @Autowired
    private VariableBindService variableBindService;


   /* @GetMapping(value = "scene/variable/manual")
    @ApiOperation(value = "根据模型id查询策列表，评分卡，以及绑定变量")
    public  Result<ActProcReleaseVo> getVariablesByActProcRealeseId(String actProcRealeseId, HttpServletRequest request) {
        Result<ActProcReleaseVo> result = null;
        // 参数检查
        logger.info("getVariablesByActProcRealeseId mothod invoke,paramter actProcRealeseId :" + actProcRealeseId);
        if(StringUtils.isEmpty(actProcRealeseId)){
            result = Result.error(1,"参数异常");
            return result;
        }
        //0.根据id查找actProRea对象
        RpcModelVerfication verfication = new RpcModelVerfication();
        verfication.setProcReleaseId(Long.parseLong(actProcRealeseId));
        Result<RpcModelReleaseInfo> rpcModelReleaseInfoResult = activitiConfigRpc.getProcReleaseById(verfication);
        if(rpcModelReleaseInfoResult == null || rpcModelReleaseInfoResult.getCode() != 0 || rpcModelReleaseInfoResult.getData() ==null){
            result = Result.success();
            result.setMsg("模型没有关联变量！");
            return result;
        }
        RpcModelReleaseInfo actProcRelease = rpcModelReleaseInfoResult.getData();
        ActProcReleaseVo actProcReleaseVo = new ActProcReleaseVo();
        String modelProcdefId = actProcRelease.getModelProcdefId();
        //1.查询模型对应属性
        actProcReleaseVo.setModelName(actProcRelease.getModelName());//获取到模型名字
        actProcReleaseVo.setModelVersion(actProcRelease.getModelVersion());//获取到模型版本
        actProcReleaseVo.setModelProcdefId(modelProcdefId);
        //获取策列表
        List<ModelSence> modelSenceList = modelSenceService.selectList(new EntityWrapper<ModelSence>().eq("MODEL_PROCDEF_ID", modelProcdefId));
        actProcReleaseVo.setVariableMap(modelSenceList);
        //查询策列列表对应的title
        for (ModelSence modelSence : modelSenceList) {
            SceneVersion sceneVersion = sceneVersionService.selectOne(new EntityWrapper<SceneVersion>().eq("version_id", modelSence.getSenceVersionId()));
            if (sceneVersion != null) {
                modelSence.setSceneName(sceneVersion.getTitle());
                modelSence.setSenceCode(sceneVersion.getSceneIdentify());
            }
        }
        //2.查询策列关联的变量
        for (ModelSence modelSence : modelSenceList) {
            List<VariableBind> variableBindList = variableBindService.selectList(new EntityWrapper<VariableBind>().eq("SENCE_VERSION_ID", modelSence.getSenceVersionId()));
            for (VariableBind variableBind : variableBindList) {
                //如果为Constant类型的则查询
                ArrayList<Map<String, String>> list = new ArrayList<>();
                String variableCode = variableBind.getVariableCode();
                variableBind.setOptionData(list);
                variableBind.setSenceCode(modelSence.getSenceCode());
                if ("CONSTANT".equals(variableBind.getDataType())) {
                    //查询单个变量对象的常量列表
                    //如果redis中无sex缓存,则查找mysql并加入缓存
                    //根据变量的constant_id查询变量code,再根据变量code查询该code对应的所有变量
                    Long constantId = variableBind.getConstantId();
                    ConstantInfo constantInfo1 = constantInfoService.selectById(constantId);
                    String conKey = constantInfo1.getConKey();
                    List<ConstantInfo> ciList = constantInfoService.selectList(new EntityWrapper<ConstantInfo>().eq("con_key", conKey).eq("con_type", "1"));
                    for (ConstantInfo constantInfo : ciList) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("value", constantInfo.getConCode());
                        map.put("name", constantInfo.getConName());
                        list.add(map);
                    }
                }
            }
            modelSence.setData(variableBindList);
        }
        result = Result.success(actProcReleaseVo);
        return result;
    }
*/
    /**
     * 为变量赋值
     * http://localhost:8765/rule/service/actProcRelease/init
     *
     * @returns
     */
    @PostMapping(value = "scene/variable/init")
    @ApiOperation(value = "给变量赋值")
    public Result addVariable(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("addVariable method invoke,paramter:"+ JSON.toJSONString(parameterMap));
        try {
            Set<String> keys = parameterMap.keySet();
            Iterator<String> key = keys.iterator();
            while (key.hasNext()) {
                String next = key.next();
                String temValue = parameterMap.get(next)[0];
                String[] strings = next.split("#");
                if(strings.length == 3){
                    EntityWrapper<VariableBind> wrapper = new EntityWrapper<>();
                    wrapper.eq("SENCE_VERSION_ID", strings[0]);
                    wrapper.eq("VARIABLE_CODE", strings[2]);
                    VariableBind variableBind = new VariableBind();
                    variableBind.setTmpValue(temValue);
                    variableBindService.update(variableBind, wrapper);
                }
            }
            Result result = Result.success();
            result.setMsg("保存成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addVariable method invoke error,exception:"+ JSON.toJSONString(parameterMap));
        }
        return Result.error(1, "保存失败");
    }


  /*  *//**
     * 为变量赋值
     * http://localhost:8765/rule/service/actProcRelease/init
     *
     * @returns
     *//*
    @PostMapping(value = "scene/variable/init/auto")
    @ApiOperation(value = "自动测试给变量赋值")
    public Result addVariableAuto(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("addVariableAuto method invoke,paramter:"+ JSON.toJSONString(parameterMap));
        try {
            Set<String> keys = parameterMap.keySet();
            Iterator<String> key = keys.iterator();
            while (key.hasNext()) {
                String next = key.next();
                if (!"amount".equals(next)) {
                    String val = parameterMap.get(next)[0];
                    String[] strings = next.split("#");
                    //String senceVersionId,String variableCode,String tmpValue,String bindTable,String bindColumn
                    String senceVersionId = strings[0];
                    String variableCode = strings[1];
                    String field = strings[2];
                    //判断是绑定表格的值还是绑定列
                    if ("table".equals(field) && val != null) {
                        variableBindService.myUpdate(senceVersionId, variableCode, null, val, null);
                    } else if ("column".equals(field) && val != null) {
                        variableBindService.myUpdate(senceVersionId, variableCode, null, null, val);
                    }
                }
            }
            Result result = Result.success();
            result.setMsg("保存成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addVariable method invoke error,exception:"+e.getMessage());
        }
        return Result.error(1, "保存失败");
    }*/

}

