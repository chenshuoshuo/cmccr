package com.lqkj.web.cmccr2.modules.log.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * 系统日志api
 */
@Api(tags = "系统日志")
@RestController
public class CcrSystemLogController {

    @Autowired
    CcrSystemLogService systemLogService;

    @GetMapping("/center/sys/log/" + APIVersion.V1 + "/list")
    @ApiOperation("分页查询系统日志")
    public MessageBean<Page<CcrSystemLog>> page(Timestamp startTime, Timestamp endTime,
                                                @RequestParam Integer page,
                                                @RequestParam Integer pageSize) {
        return MessageBean.ok(systemLogService.page(startTime, endTime, page, pageSize));
    }
}
