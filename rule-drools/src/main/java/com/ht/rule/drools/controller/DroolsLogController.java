package com.ht.rule.drools.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.api.entity.DroolsDetailLog;
import com.ht.rule.common.api.entity.DroolsLog;
import com.ht.rule.common.api.entity.EntityInfo;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.result.Result;
import com.ht.rule.drools.service.DroolsDetailLogService;
import com.ht.rule.drools.service.DroolsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 规则引擎实体信息表 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@RestController
@RequestMapping("/log")
@Api(tags = "DroolsLogController", description = "日志查询", hidden = true)
public class DroolsLogController extends BaseController {

    @Autowired
    private DroolsLogService droolsLogService;
    @Autowired
    private DroolsDetailLogService droolsDetailLogService;
    @GetMapping("/getDetails")
    @ApiOperation(value = "查询所有的对象")
    public PageResult<List<DroolsDetailLog>> getDetails(Integer logId,Integer page , Integer limit){

        Wrapper<DroolsDetailLog> wrapper = new EntityWrapper<>();
        //流水号
        wrapper.eq(DroolsDetailLog.DROOLS_LOGID,logId);
        Page<DroolsDetailLog> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);
        pages = droolsDetailLogService.selectPage(pages, wrapper);
        return PageResult.success(pages.getRecords(), pages.getTotal());
    }
    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public PageResult<List<DroolsLog>> page(String date, String endDate,String key ,String businessId, Integer page , Integer limit){
        Wrapper<DroolsLog> wrapper = new EntityWrapper<>();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(date)) {
            wrapper.and().ge("create_time", date);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(endDate)) {
            wrapper.and().le("create_time", endDate);
        }
        if(StringUtils.isNotBlank(key)){
            wrapper.like(DroolsLog.SENCE_NAME,key);
          //  wrapper.or().like(DroolsLog.VERSION_NUM,key);
           // wrapper.or().like("entity_identify",key);
        }
        wrapper.orderBy(DroolsLog.CREATE_TIME,false);
         Page<DroolsLog> pages = new Page<>();
         pages.setCurrent(page);
         pages.setSize(limit);
        pages = droolsLogService.selectPage(pages,wrapper);
        return PageResult.success(pages.getRecords(),pages.getTotal());
    }


}

