package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.CcrAppRequestRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.List;

@Api(tags = "应用访问记录")
@RestController
@RequestMapping("/center/appRecord/")
public class AppRequestRecordController {

    @Autowired
    CcrAppRequestRecordService ccrAppRequestRecordService;

    @ApiOperation("增加访问记录")
    @PutMapping(APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(CcrAppRequestRecord requestRecord) {
        return new WebAsyncTask<>(() -> {
            ccrAppRequestRecordService.add(requestRecord);
            return null;
        });
    }

    @ApiOperation("查询app访问统计")
    @GetMapping(APIVersion.V1 + "/topRequest")
    public MessageBean<List<Object[]>> topRequest() {
        return MessageBean.ok(ccrAppRequestRecordService.topRequest());
    }
}
