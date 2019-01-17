package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.MenuClickRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "网关统计")
@RestController
public class MenuClickRecordController {
    private MenuClickRecordService menuClickRecordService;

    public MenuClickRecordController(MenuClickRecordService menuClickRecordService) {
        this.menuClickRecordService = menuClickRecordService;
    }

    @ApiOperation("增加点击菜单记录")
    @PutMapping("/center/click/menu/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(@RequestBody CcrMenuClickRecord clickRecord) {
        return new WebAsyncTask<>(() -> {
            menuClickRecordService.add(clickRecord);
            return null;
        });
    }

    @ApiOperation("查询菜单点击接口")
    @GetMapping("/center/click/menu/" + APIVersion.V1 + "/record")
    public MessageListBean<Object[]> clickRecord() {
        return MessageListBean.ok(menuClickRecordService.record());
    }
}
