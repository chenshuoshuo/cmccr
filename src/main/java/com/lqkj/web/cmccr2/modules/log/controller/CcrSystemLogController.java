package com.lqkj.web.cmccr2.modules.log.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.sql.Timestamp;
import java.util.Date;

import static com.lqkj.web.cmccr2.modules.application.domain.CcrPcApplication.IpsApplicationPlatform.system;

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

    @GetMapping("/center/sys/log/" + APIVersion.V1 + "/export")
    @ApiOperation("导出系统日志")
    public ResponseEntity<StreamingResponseBody> export(@RequestParam Timestamp startTime,
                                                        @RequestParam Timestamp endTime) {
        StreamingResponseBody body = outputStream -> {
            systemLogService.export(startTime, endTime, outputStream);
        };

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=logs.xlsx")
                .body(body);
    }

    public static void main(String[] args) {
        Date date=new Date();
        System.out.println(date.getTime());
    }
}
