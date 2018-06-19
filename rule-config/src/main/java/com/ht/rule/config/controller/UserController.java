package com.ht.rule.config.controller;


import com.ht.rule.common.controller.BaseController;
import com.ht.rule.common.result.Result;
import com.ht.ussp.client.dto.LoginInfoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("getUserInfo")
    @ApiOperation(value = "获取登录用户信息")
    public Result<LoginInfoDto> getLoginInfo(){
        LoginInfoDto user = userInfoHelper.getLoginInfo();
        return Result.success(user);

    }


}

