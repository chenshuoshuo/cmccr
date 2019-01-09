package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrClickRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.ClickRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "网关统计")
@RestController
public class ClickRecordController {

    private ClickRecordService clickRecordService;

    public ClickRecordController(ClickRecordService clickRecordService) {
        this.clickRecordService = clickRecordService;
    }

    @ApiOperation("增加点击地图记录")
    @PutMapping("/center/click/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(@RequestBody CcrClickRecord clickRecord) {
        return new WebAsyncTask<>(() -> {
            clickRecordService.add(clickRecord);
            return null;
        });
    }
}
