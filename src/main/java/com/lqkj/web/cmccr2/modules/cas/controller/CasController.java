package com.lqkj.web.cmccr2.modules.cas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.store.domain.CcrStoreItem;
import com.lqkj.web.cmccr2.modules.store.service.StoreService;
import com.lqkj.web.cmccr2.utils.HttpUtils;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import okhttp3.Response;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.Map;

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


    @Autowired
    private StoreService storeService;

    @ApiOperation("cas单点登录获取用户信息")
    @GetMapping("/center/cas/" + APIVersion.V1 + "/getUser/")
    public String casToUser(@ApiParam(name = "casLoginUrl",value = "获取用户信息访问地址",required = true)@RequestParam(required = true)String casLoginUrl){
        try {
            ObjectMapper mapper=new ObjectMapper();
            CcrStoreItem ccrStoreItem = storeService.get("configuration", "runtimeConfig");
            if(ccrStoreItem!=null && StringUtils.isNotEmpty(ccrStoreItem.getValue())){
                Map runtimeConfig = mapper.readValue(ccrStoreItem.getValue(), Map.class);
                Object userConfig = runtimeConfig.get("userConfig");
                if(userConfig!=null){
                    if(runtimeConfig instanceof Map){
                        Map userConfigMap = (Map) userConfig;
                        Boolean isSupportCas = Boolean.valueOf(userConfigMap.get("isSupportCas").toString());
                        if(isSupportCas){
                            String casUrl = userConfigMap.get("casLoginUrl").toString();
                            java.net.URL urls = new java.net.URL(casUrl);
                            java.net.URL loginUrl = new java.net.URL(casLoginUrl);
                            if(urls.getHost().equals(loginUrl.getPath())){
                                Response response=HttpUtils.getInstance().getRequest(casLoginUrl);
                                return response.body().string();
                            }
                        }
                    }
                }
            }
            return mapper.writeValueAsString(MessageBean.error("单点登陆域名验证失败"));
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        String url="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=java%20%E5%A6%82%E4%BD%95%E4%BF%9D%E7%95%99%E7%BD%91%E5%9D%80%E4%B8%AD%E7%9A%84%E5%9F%9F%E5%90%8D&rsv_pq=a836a1ee0003f627&rsv_t=611cx%2Bw1sCcJQ%2BydDYDlKIxj%2B9aNLXg%2B31Pkfj2kM6c0OhlP9LUnvvwcGwk&rqlang=cn&rsv_enter=1&rsv_sug3=68&rsv_sug1=8&rsv_sug7=101&rsv_sug2=0&inputT=24697&rsv_sug4=25423";
        String domain="";
        try {
            java.net.URL urls = new java.net.URL(url);
            String host = urls.getHost();// 获取主机名
            domain = host;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("domain="+domain);

    }
}
