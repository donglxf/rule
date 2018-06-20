package com.ht.rule.config.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.PageResult;
import com.ht.rule.common.result.Result;
import com.ht.rule.common.api.entity.BusinessSystem;
import com.ht.rule.config.service.BusinessSystemService;
import com.ht.rule.config.util.anno.OperationDelete;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 业务系统表
 * </p>
 *
 * @author 张鹏
 * @since 2018-04-08
 */
@RestController
@RequestMapping("/businessSystem")
public class BusinessSystemController extends BaseController {

    @Autowired
    private BusinessSystemService businessSystemService;

    @GetMapping("page")
    @ApiOperation(value = "分页查询")
    public PageResult<List<BusinessSystem>> page(String key, Integer page, Integer limit) {
        Wrapper<BusinessSystem> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            wrapper.like(BusinessSystem.BUSINESS_SYSTEM_NAME,key);
            wrapper.or().like(BusinessSystem.BUSINESS_SYSTEM_CODE, key);
        }
        Page<BusinessSystem> pages = new Page<>();
        pages.setCurrent(page);
        pages.setSize(limit);
        pages = businessSystemService.selectPage(pages, wrapper);
        return PageResult.success(pages.getRecords(), pages.getTotal());
    }


    @GetMapping("getAll")
    @ApiOperation(value = "查询所有的系统和业务线")
    public Result<List<BusinessSystem>>  getAll(String key){
        List<BusinessSystem> list = businessSystemService.findSysBusinessList(key);
        return Result.success(list);
    }


    @PostMapping("edit")
    @ApiOperation(value = "动作新增or修改")
    @Transactional()
    public Result<Integer> edit(BusinessSystem businessSystem) {

        //修改操作
        if(StringUtils.isNotBlank(businessSystem.getBusinessSystemlId())){
            businessSystem.setUpdateTime(new Date());
            businessSystem.setUpdateUserId(getUserId());
        }else{
            businessSystem.setCreateTime(new Date());
            businessSystem.setCreateUserId(getUserId());
        }
        businessSystemService.insertOrUpdate(businessSystem);
        return Result.success(0);
    }

    @GetMapping("delete")
    @ApiOperation(value = "通过id删除信息")
    @OperationDelete(tableColumn = {"rule_business_type&business_system_id"},idVal = "#id")
    public Result<Integer> delete( String  id) {
        businessSystemService.deleteById(id);
        return Result.success(0);
    }

    @GetMapping("getInfoById")
    @ApiOperation(value = "通过id查询详细信息")
    public Result<BusinessSystem> getDateById(String  id) {
        BusinessSystem entityInfo = businessSystemService.selectById(id);
        return Result.success(entityInfo);
    }

}

