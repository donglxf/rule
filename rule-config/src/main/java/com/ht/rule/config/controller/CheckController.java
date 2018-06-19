package com.ht.rule.config.controller;


import com.ht.rule.config.facade.CheckKeyFacade;
import com.ht.rule.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 规则引擎常用变量 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2017-12-15
 */
@RestController
@RequestMapping("/check")
@Api(tags = "CheckController", description = "验证", hidden = true)
public class CheckController {
    @Autowired
    private CheckKeyFacade checkKeyFacade;


    @GetMapping("key")
    @ApiOperation(value = "查询所有的常量")
    public Result<Integer> getAll(String key,Integer type,String other,String id,String other2){
        if(StringUtils.isNotBlank(other2)){
            other += "_"+other2;
        }
        boolean flag = checkKeyFacade.checkKey(key,type,other,id);
        if(flag){
            return Result.error(-1,"保存失败，该标识已存在！");
        }else{
            return Result.success(1);
        }
    }


}

