package com.lqkj.web.cmccr2.modules.request.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.request.serivce.CcrRequestRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "网关统计")
@RestController
public class CcrRequestRecordController {

    @Autowired
    CcrRequestRecordService requestRecordService;

    @ApiOperation("增加请求记录")
    @PutMapping("/center/record/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(CcrRequestRecord requestRecord) {
        return new WebAsyncTask<>(() -> {
            requestRecordService.add(requestRecord);
            return null;
        });
    }
}
