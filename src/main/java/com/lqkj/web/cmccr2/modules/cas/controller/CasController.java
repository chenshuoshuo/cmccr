package com.lqkj.web.cmccr2.modules.cas.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.utils.HttpUtils;
import io.micrometer.core.instrument.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public String casToUser(@ApiParam(name = "casLoginUrl",value = "获取用户信息访问地址",required = true)@RequestParam(required = true)String casLoginUrl){
        try {
            Response response=HttpUtils.getInstance().getRequest(casLoginUrl);
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

}
