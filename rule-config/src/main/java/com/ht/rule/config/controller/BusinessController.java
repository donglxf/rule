package com.ht.rule.config.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.api.entity.*;
import com.ht.rule.config.service.BusinessService;
import com.ht.rule.config.service.EntityInfoService;
import com.ht.rule.config.util.anno.OperationDelete;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.api.entity.Business;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private EntityInfoService entityInfoService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询")
    public PageResult<List<Business>> page(String key , Integer page , Integer limit){
        Wrapper<Business> wrapper = new EntityWrapper<>();
        if(StringUtils.isNotBlank(key)){
            wrapper.like(Business.BUSINESS_TYPE_NAME,key);
            wrapper.or().like(Business.BUSINESS_TYPE_CODE,key);
        }
        wrapper.orderBy(Business.CREATE_TIME,false);
        Page<Business> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);
        pages = businessService.selectPage(pages,wrapper);
        return PageResult.success(pages.getRecords(),pages.getTotal());
    }

    @GetMapping("getAll")
    @ApiOperation(value = "查询所有")
    public Result<List<Business>>  getAll(String key){
        Wrapper<Business> wrapper = new EntityWrapper<>();
        wrapper.orderBy(Business.CREATE_TIME,false);
        //List<Business> list = businessService.selectSysBusinessView(key);
        List<Business> list = businessService.selectList(wrapper);
        return Result.success(list);
    }

    @GetMapping("getAllBySys")
    @ApiOperation(value = "查询通过系统")
    public Result<List<Business>>  getAllBySys(String sysId){
        Wrapper<Business> wrapper = new EntityWrapper<>();
        wrapper.eq(Business.BELONG_SYSTEM_LOCAL_ID,sysId);
        wrapper.orderBy(Business.CREATE_TIME,true);
        List<Business> list = businessService.selectList(wrapper);
        return Result.success(list);
    }

    @PostMapping("edit")
    @ApiOperation(value = "新增")
    @Transactional()
    @CacheEvict(value = "risk-rule",key = "getIdCodeMap")
    public Result<Integer> edit( @Validated Business business){
        business.setCreateTime(new Date());
        business.setCreateUserId(this.getUserId());
        businessService.insertOrUpdate(business);
        return Result.success(0);
    }

    @GetMapping("getInfoById")
    @ApiOperation(value = "通过id查询详细信息")
    public Result<Business> getDateById(String id){
        Business business = businessService.selectById(id);
        return Result.success(business);
    }

    @GetMapping("delete")
    @ApiOperation(value = "通过id删除信息")
    @OperationDelete(tableColumn = {"rule_entity_info&business_id","rule_action_info&business_id"},idVal = "#id")
    @CacheEvict(value = "risk-rule",key = "getIdCodeMap4Business")
    public Result<Integer> delete( Long id){
        //判断是否有其他绑定数据
        businessService.deleteById(id);
        return Result.success(0);
    }


}

