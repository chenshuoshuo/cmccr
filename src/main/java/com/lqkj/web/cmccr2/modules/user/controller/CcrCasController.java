package com.lqkj.web.cmccr2.modules.user.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.user.service.CcrCasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "cas处理")
@RestController
public class CcrCasController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrCasService casService;

    @ApiOperation("cas回调地址")
    @GetMapping("/center/cas/callback")
    public String cas(@RequestParam String ticket) {
        logger.info("收到cas ticket:{}", ticket);
        return ticket;
    }

    @ApiOperation("获取cas用户信息")
    @PostMapping("/center/cas/user/")
    public MessageBean<String> user(@ApiIgnore Authentication authentication,
                            @RequestParam String ticket) {
        casService.updateTicket(authentication.getName(), ticket);
        return MessageBean.ok(ticket);
    }
}
