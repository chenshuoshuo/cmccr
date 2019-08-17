package com.lqkj.web.cmccr2.modules.asr.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.asr.domain.BaiduAsrConfig;
import com.lqkj.web.cmccr2.modules.asr.service.BaiduAsrConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Api(tags = "百度语音API配置")
@RestController
@RequestMapping("/center/asr/")
public class BaiduAsrConfigController {

    public static final String VERSION = "v1";

    @Autowired
    BaiduAsrConfigService baiduAsrConfigService;

    @ApiOperation("获取API配置")
    @GetMapping(VERSION + "/loadConfig")
    public MessageBean<ObjectNode> loadConfig(){
        return MessageBean.ok(baiduAsrConfigService.loadConfig());
    }

    @ApiOperation("保存API配置")
    @PutMapping(VERSION + "/saveConfig")
    public MessageBean<ObjectNode> saveConfig(BaiduAsrConfig config){
        return MessageBean.ok(baiduAsrConfigService.saveConfig(config));
    }

    @ApiOperation("获取token")
    @GetMapping(VERSION + "/loadToken")
    public MessageBean<ObjectNode> loadToken(){
        return MessageBean.ok(baiduAsrConfigService.loadToken());
    }

    @ApiOperation("刷新token")
    @PostMapping(VERSION + "/refreshToken")
    public MessageBean<ObjectNode> refreshToken() throws IOException {
        return MessageBean.ok(baiduAsrConfigService.refreshToken());
    }
}
