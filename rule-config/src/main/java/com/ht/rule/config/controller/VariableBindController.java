package com.ht.rule.config.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.*;
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

    protected static final Logger log = LoggerFactory.getLogger(VariableBindController.class);
    @Autowired
    private VariableBindService variableBindService;

    @Autowired
    private SceneVersionService sceneVersionService;

    @Autowired
    private EntityItemInfoService entityItemInfoService;


    @Autowired
    private SenceVerficationBatchService senceVerficationBatchService;

    @Autowired
    private ConstantInfoService constantInfoService;

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

  /*  @PostMapping("manualVariable")
    @ApiOperation(value = "规则手动验证")
    public PageResult<Map<String, Object>> manualVariable(VariableBindVo entityInfo, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        DroolsParamter drools = new DroolsParamter();
        // 插入一笔记录到批次表
        SenceVerficationBatch batch = new SenceVerficationBatch();
        batch.setBatchSize(1);
        batch.setSenceVersionId(String.valueOf(entityInfo.getSenceVersionId()));
        batch.setVerficationType(String.valueOf(VerficationTypeEnum.manu.getValue()));
        batch.setCreateUser(this.getUserId());
        senceVerficationBatchService.insert(batch);

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

        drools.setVersionId(entityInfo.getSenceVersionId()); // 版本表id
        drools.setType(RuleCallTypeEnum.rule.getType());
        drools.setVersion(String.valueOf(entityInfo.getVersion())); // 版本号
        drools.setSence(entityInfo.getSceneIdentify());
        drools.setBatchId(String.valueOf(batch.getId()));
        drools.setData(data);


        // 规则验证返回结果处理
        RuleExcuteResult result  = droolsExcuteFacade.excuteDroolsSceneValidation(drools);
       // String res = String.valueOf(result);
        Map<String, Object> resultMap = new HashMap<String, Object>();
       // JSONObject obj = JSON.parseObject(res);
       if(result.getCode() == 0){
           resultMap.put("logId", result.getData().getLogIdList().get(0));
           resultMap.put("versionId", entityInfo.getSenceVersionId());
           return PageResult.success(resultMap, 0);
       }
       else {
            return PageResult.error(1,result.getMsg());
        }

    }*/

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

   /* public List<Map<String, Object>> getAutoValidaionData(List<VariableBind> bindList, VariableBindVo entityInfo) throws Exception{
        StringBuffer buf = new StringBuffer(RuleConstant.SELECT + " ");
        String column = "", table = "";
        String getWay = entityInfo.getGetWay();
        for (VariableBind vb : bindList) {
            column += vb.getBindColumn() + " as " + vb.getVariableCode() + ",";
            table = vb.getBindTable();
        }
        buf.append(column.substring(0, column.length() - 1)).append(" FROM ").append(table).append(" where ")
                .append(RuleConstant.SCENE_ID).append("='").append(entityInfo.getSenceId()).append("' ");
        if ("0".equals(getWay)) { // 随机取值
            buf.append(" AND rand() ");
        } else {
            buf.append(" AND 1=1 ");
        }
        buf.append(" limit " + entityInfo.getExcuteTotal());
        log.info("自动验证数据获取sql================" + buf.toString());
        try {
            List<Map<String, Object>> obj = tempDataContainsService.getAutoValidaionData(buf.toString());
            log.info("=========================" + JSON.toJSONString(obj));
            return obj;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new Exception("数据绑定异常，请检查变量绑定信息是否正确");
        }
    }
*/
 /*   @PostMapping("getAutoValidaionData")
    @ApiOperation(value = "根据规则id获取需要验证的数据")
    public List<Map<String, Object>> getAutoValidaionData(VariableBindVo entityInfo) throws Exception {
        List<VariableBind> bindList = getVariableBindBySenceVersionId(entityInfo);
        List<Map<String, Object>> recordMap = getAutoValidaionData(bindList, entityInfo); // 查询所需验证数据

        return recordMap;
    }*/

    /*@PostMapping("autoVariable")
    @ApiOperation(value = "规则自动验证")
    public PageResult<List<Map<String, Object>>> autoVariable(VariableBindVo entityInfo, HttpServletRequest request) throws Exception {
        int success = 0, fail = 0;
        try {
            List<Map<String, Object>> recordMap = getAutoValidaionData(entityInfo); // 查询所需验证数据
            if (ObjectUtils.isEmpty(recordMap)) {
                return PageResult.error(1, "验证数据为空，请检查配置或数据!");
            }
            // 插入一笔记录到批次表
            SenceVerficationBatch batch = new SenceVerficationBatch();
            batch.setBatchSize(recordMap.size());
            batch.setSenceVersionId(String.valueOf(entityInfo.getSenceVersionId()));
            batch.setVerficationType(String.valueOf(VerficationTypeEnum.auto.getValue()));
            batch.setCreateUser(this.getUserId());
            senceVerficationBatchService.insertOrUpdate(batch);

            List<DroolsParamter> list = new ArrayList<DroolsParamter>();
            for (Map<String, Object> map2 : recordMap) {
                Map<String, Object> data = new HashMap<String, Object>();
                for (Map.Entry<String, Object> ma : map2.entrySet()) {
                    data.put(ma.getKey(), ma.getValue());
                }
                DroolsParamter drools = new DroolsParamter();
                drools.setVersion(String.valueOf(entityInfo.getVersion()));
                drools.setType(RuleCallTypeEnum.rule.getType());
    //            drools.setVersion(String.valueOf(entityInfo.getSenceVersionId()));
                drools.setSence(entityInfo.getSceneIdentify());
                drools.setData(data);
                drools.setBatchId(String.valueOf(batch.getId()));
                list.add(drools);
            }

            // 处理规定调用返回结果
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            String res = droolsRuleRpc.batchExcuteRuleValidation(list);
            JSONArray mainArray = JSONArray.parseArray(res);
            for (int i = 0; i < mainArray.size(); i++) {
                JSONObject obj = mainArray.getJSONObject(i);
                JSONObject o = obj.getJSONObject("data");
                JSONArray dataArr = o.getJSONArray("logIdList");
                JSONArray count = (JSONArray) o.get("ruleList");
                List<String> ruleList = new ArrayList<String>();
                if (ObjectUtils.isNotEmpty(count)) {
                    String[] ruleArr = count.toArray(new String[count.size()]);
                    Set set = new HashSet();
                    for (int j = 0; j < ruleArr.length; j++) {
                        set.add(ruleArr[j]);
                    }
                    ruleList.addAll(set);
                }
                String[] arg = dataArr.toArray(new String[dataArr.size()]);
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("logId", arg[0]);
                resultMap.put("versionId", entityInfo.getSenceVersionId());
                resultMap.put("count", ruleList.size());
    //            resultMap.put("variableMap", variableMap);
                resultList.add(resultMap);
            }
            return PageResult.success(resultList, 0);
        }catch(Exception ex){
            return PageResult.error(1, ex.getMessage());
        }

    }*/

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