package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.MapClickRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "网关统计")
@RestController
public class MapClickRecordController {

    private MapClickRecordService mapClickRecordService;

    public MapClickRecordController(MapClickRecordService mapClickRecordService) {
        this.mapClickRecordService = mapClickRecordService;
    }

    @ApiOperation("增加点击地图记录")
    @PutMapping("/center/click/map/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(@RequestBody CcrMapClickRecord clickRecord) {
        return new WebAsyncTask<>(() -> {
            mapClickRecordService.add(clickRecord);
            return null;
        });
    }

    @ApiOperation("查询地图点击接口")
    @GetMapping("/center/click/map/" + APIVersion.V1 + "/record")
    public MessageListBean<Object[]> clickRecord() {
        return MessageListBean.ok(mapClickRecordService.clickRecord());
    }
}
