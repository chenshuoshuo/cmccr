package com.lqkj.web.cmccr2.modules.cas.controller;

import com.lqkj.web.cmccr2.APIVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CasLoginController
 * @Description TODO
 * @Author Wells
 * @Date 2019/7/17 9:15
 * @Version 1.0
 **/
@Api(tags = "cas单点登录")
@RestController
public class CasController {

    @ApiOperation("cas单点登录获取用户信息")
    @GetMapping("/center/cas/" + APIVersion.V1 + "/getUser/")
    public String casToUser(@ApiParam(name = "carLoginUrl",value = "获取用户信息访问地址",required = true)@RequestParam(required = true)String carLoginUrl){
        return "asdfa";
    }

}
