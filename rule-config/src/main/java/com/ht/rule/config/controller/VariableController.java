package com.ht.rule.config.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.rule.common.api.entity.Variable;
import com.ht.rule.config.service.VariableService;
import com.ht.rule.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 规则引擎常用变量 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@RestController
@RequestMapping("/variable")
@Api(tags = "VariableController", description = "常量相关api描述", hidden = true)
public class VariableController {
    @Autowired
    private VariableService variableService;
    @GetMapping("getAll")
    @ApiOperation(value = "查询所有的常量")
    public Result<List<Variable>> getAll(){
        List<Variable> list = variableService.selectList(null);
        return Result.success(list);
    }

    @GetMapping("getByIds")
    @ApiOperation(value = "查询所有的常量")
    public Result<List<Variable>> getAll(String ids){

        Wrapper<Variable> wrapper = new EntityWrapper<>();
        wrapper.in("variable_id",ids);
        List<Variable> list = variableService.selectList(wrapper);
        return Result.success(list);
    }

}

