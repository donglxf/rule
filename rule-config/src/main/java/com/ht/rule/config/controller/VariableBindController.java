package com.ht.rule.config.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.common.api.vo.ActProcRelease;
import com.ht.rule.common.api.vo.ModelSence;
import com.ht.rule.common.api.vo.RuleHisVersionVo;
import com.ht.rule.common.comenum.RuleCallTypeEnum;
import com.ht.rule.common.comenum.VerficationTypeEnum;
import com.ht.rule.common.constant.RuleConstant;
import com.ht.rule.common.vo.model.drools.DroolsParamter;
import com.ht.rule.common.vo.model.drools.RuleExcuteResult;
import com.ht.rule.config.rpc.DroolsExcuteFeginClient;
import com.ht.rule.config.service.*;
import com.ht.rule.common.api.vo.VariableBindVo;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.util.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dyb
 * @since 2018-01-10
 */
@RestController
@RequestMapping("/variableBind/")
public class VariableBindController extends BaseController {

    @Autowired
    private VariableBindService variableBindService;

    @Autowired
    private SceneVersionService sceneVersionService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private RuleHisVersionService ruleHisVersionService;
    @Autowired
    private SceneInfoService sceneInfoService;

    @Autowired
    private ConstantInfoService constantInfoService;

    @Autowired
    private DroolsExcuteFeginClient droolsExcuteFeginClient;

    @Autowired
    private RedisTemplate<String, String> redis;

    @GetMapping("getInfoById")
    @ApiOperation(value = "通过id查询详细信息")
    public Result<SceneVersion> getDateById(Long id) {
        SceneVersion entityInfo = sceneVersionService.selectById(id);
        return Result.success(entityInfo);
    }

    @GetMapping("getVariableBind")
    @ApiOperation(value = "根据版本查询规则绑定变量")
    public PageResult<List<VariableBind>> getVariableBind(@RequestParam(name = "versionId") String versionId) {
        Wrapper<VariableBind> wrapper = new EntityWrapper<>();
        wrapper.eq("sence_version_id", versionId);
        List<VariableBind> list = variableBindService.selectList(wrapper);
        return PageResult.success(list, 0);
    }



    @PostMapping("edit")
    @ApiOperation(value = "新增")
    @Transactional()
    public Result<Integer> edit(VariableBind entityInfo, HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Wrapper<VariableBind> wrapper = new EntityWrapper<>();
        wrapper.eq("sence_version_id", map.get("senceVersionid")[0]);
        List<VariableBind> list = variableBindService.selectList(wrapper);
        for (VariableBind var : list) {
            String varCode = var.getVariableCode();
            entityInfo.setId(Long.parseLong(map.get(varCode + "_bind")[0]));
            entityInfo.setBindTable(map.get(varCode + "_tableName")[0]);
            entityInfo.setBindColumn(map.get(varCode + "_column")[0]);
            entityInfo.setCreateTime(new Date());
            entityInfo.setIsEffect("1");
            entityInfo.setCreateUser(this.getUserId());
            variableBindService.insertOrUpdate(entityInfo);
        }
        // 更新版本信息表 绑定状态
        SceneVersion sc = sceneVersionService.selectById(map.get("senceVersionid")[0]);
        sc.setIsBindVar("1");
        sc.setCreUserId(getUserId());
        sceneVersionService.insertOrUpdate(sc);
        return Result.success(0);
    }

    @GetMapping("getAll")
    @ApiOperation(value = "手动验证时，根据版本查询规则绑定变量")
    public ActProcRelease getAll(@RequestParam(name = "versionId") String versionId) {
        Wrapper<VariableBind> wrapper = new EntityWrapper<>();
        wrapper.eq("sence_version_id", versionId);
        List<VariableBind> list = variableBindService.selectList(wrapper);
        for (VariableBind bind : list) {
            ArrayList<Map<String, String>> constantList = new ArrayList<>();
            String variableCode = bind.getVariableCode();
            Long constantId = bind.getConstantId(); // 常量id
            if ("CONSTANT".equals(bind.getDataType())) {
                Long val = redis.opsForList().size(variableCode + "name");
                if (val == 0L) {
                    ConstantInfo info = constantInfoService.selectById(constantId);
                    List<ConstantInfo> ciList = constantInfoService.selectList(new EntityWrapper<ConstantInfo>().eq("con_key", info.getConKey()).eq("con_type", "1"));
                    for (ConstantInfo constantInfo : ciList) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("value", constantInfo.getConCode());
                        map.put("name", constantInfo.getConName());
                        constantList.add(map);
                        //缓存数据
                        redis.opsForList().leftPush(variableCode + "name", constantInfo.getConName());
                        redis.opsForList().leftPush(variableCode + "value", constantInfo.getConCode());
                    }
                } else {
                    for (; val-- > 0; ) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("value", redis.opsForList().index(variableCode + "value", val));
                        map.put("name", redis.opsForList().index(variableCode + "name", val));
                        constantList.add(map);
                    }
                }
                bind.setOptionData(constantList);
            }
        }
        SceneVersion version = sceneVersionService.selectById(versionId);
        ModelSence sence = new ModelSence();
        sence.setData(list);
        sence.setSceneName(version.getTitle());
        List<ModelSence> model = new ArrayList<ModelSence>();
        model.add(sence);
        ActProcRelease result = new ActProcRelease();
        result.setVariableMap(model);
        return result;
    }

    @PostMapping("manualVariable")
    @ApiOperation(value = "规则手动验证")
    public PageResult<Map<String, Object>> manualVariable(VariableBindVo entityInfo, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        DroolsParamter drools = new DroolsParamter();
        // 插入一笔记录到批次表
/*        SenceVerficationBatch batch = new SenceVerficationBatch();
        batch.setBatchSize(1);
        batch.setSenceVersionId(String.valueOf(entityInfo.getSenceVersionId()));
        batch.setVerficationType(String.valueOf(VerficationTypeEnum.manu.getValue()));
        batch.setCreateUser(this.getUserId());
        senceVerficationBatchService.insert(batch);*/

        //封装规则验证所需数据
        ActProcRelease act = getAll(String.valueOf(entityInfo.getSenceVersionId()));
        List<ModelSence> map = act.getVariableMap();
        for (ModelSence sence : map) {
            List<VariableBind> li = sence.getData();
            for (VariableBind ls : li) {
                //转化类型
                if("Double".equals(ls.getDataType())){
                    data.put(ls.getVariableCode(), Double.parseDouble(ls.getTmpValue()));
                }else if("Integer".equals(ls.getDataType())){
                    data.put(ls.getVariableCode(), Integer.parseInt(ls.getTmpValue()));
                }else{
                    data.put(ls.getVariableCode(), ls.getTmpValue());
                }
            }
        }
        //查询出决策
        SceneInfo sceneInfo = sceneInfoService.selectById(entityInfo.getSenceId());
        drools.setSence(entityInfo.getSceneIdentify());
        drools.setVersion(String.valueOf(entityInfo.getVersion())); // 版本号
        drools.setData(data);
        drools.setType("2");
        Map<String,String > codeMap = businessService.getIdCodeMap();
        drools.setBusinessCode(codeMap.get(sceneInfo.getBusinessId()));
        //drools.setBusinessCode();
        RuleExcuteResult result;
        // 规则验证返回结果处理
        if(sceneInfo.getSceneType().equals(2)){
             result  = droolsExcuteFeginClient.excuteDroolsGrade(drools);
        }else{
             result  = droolsExcuteFeginClient.excuteDroolsScene(drools);
        }

       // String res = String.valueOf(result);
        Map<String, Object> resultMap = new HashMap<String, Object>();
       // JSONObject obj = JSON.parseObject(res);
       if(result.getCode() == 0){
          // resultMap.put("logId", result.getData().getLogIdList().get(0));
           resultMap.put("versionId", entityInfo.getSenceVersionId());
           //组装可以查看的数据
           List<RuleHisVersionVo> list = ruleHisVersionService.getRuleValidationResultNew(entityInfo.getSenceVersionId(),result.getData());
           resultMap.put("result",list);
           resultMap.put("scope",result.getGrade());
           return PageResult.success(resultMap, 0);
       }
       else {
            return PageResult.error(1,result.getMsg());
        }

    }

    @PostMapping("getVariableBindBySenceVersionId")
    @ApiOperation(value = "根据规则版本获取规则变量绑定信息")
    public List<VariableBind> getVariableBindBySenceVersionId(VariableBindVo entityInfo) {
        // 查询变量绑定字段信息
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("SENCE_VERSION_ID", entityInfo.getSenceVersionId());
        columnMap.put("IS_EFFECT", "1");
        List<VariableBind> bindList = variableBindService.selectByMap(columnMap);
        return bindList;
    }

    @PostMapping("development")
    @ApiOperation(value = "发布正式版")
    @Transactional()
    public Result<Integer> development(VariableBindVo bindInfo) {
        SceneVersion scene = sceneVersionService.selectById(bindInfo.getSenceVersionId()); // 当前要发布的测试版本记录
        // 最大正式版本号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sceneId", bindInfo.getSenceId());
        map.put("sceneIdentify", bindInfo.getSceneIdentify());
        map.put("type", 1);
        Map<String, Object> maxVersionMap = sceneVersionService.getMaxTestVersion(map);
        float maxVersion = 1.0f;
        if (ObjectUtils.isNotEmpty(maxVersionMap)) {
            maxVersion = Float.parseFloat(((String) maxVersionMap.get("maxVersion"))) + 0.1f;
        }
        scene.setOfficialVersion(String.valueOf(maxVersion));
        scene.setCreUserId(getUserId());
        sceneVersionService.insertOrUpdate(scene);


        /*SceneVersion entityInfo = new SceneVersion();
        entityInfo.setStatus(1);
        entityInfo.setVersion(String.valueOf(maxVersion));
        entityInfo.setType(1);
        entityInfo.setTitle(scene.getTitle());
        entityInfo.setComment(scene.getComment());
        entityInfo.setSceneIdentify(scene.getSceneIdentify());
        entityInfo.setSceneId(scene.getSceneId());
        entityInfo.setRuleDiv(scene.getRuleDiv());
        entityInfo.setRuleDrl(scene.getRuleDrl());
        sceneVersionService.insertOrUpdate(entityInfo);*/
        return Result.success(0);
    }


}
