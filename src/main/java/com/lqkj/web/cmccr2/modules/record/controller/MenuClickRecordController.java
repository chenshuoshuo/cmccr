package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.MenuClickRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.sql.Timestamp;
import java.util.Date;

@Api(tags = "点击菜单记录")
@RestController
public class MenuClickRecordController {
    private MenuClickRecordService menuClickRecordService;

    public MenuClickRecordController(MenuClickRecordService menuClickRecordService) {
        this.menuClickRecordService = menuClickRecordService;
    }

    @ApiOperation("增加点击菜单记录-Object")
    @PutMapping("/center/click/menu/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(@RequestBody CcrMenuClickRecord clickRecord) {
        return new WebAsyncTask<>(() -> {
            menuClickRecordService.add(clickRecord);
            return null;
        });
    }

    @ApiOperation("增加点击菜单记录")
    @PutMapping("/center/click/menu/" + APIVersion.V2 + "/add")
    public MessageBean<CcrMenuClickRecord> addRecord(@ApiParam(name = "menuId", value = "菜单ID", required = true) @RequestParam(required = true)Long menuId,
                                        @ApiParam(name = "name", value = "菜单名称", required = true) @RequestParam(required = true)String name) {
            CcrMenuClickRecord ccrMenuClickRecord = new CcrMenuClickRecord();
            ccrMenuClickRecord.setMenuId(menuId);
            ccrMenuClickRecord.setName(name);
            ccrMenuClickRecord.setCreateTime(new Timestamp(new Date().getTime()));
            return MessageBean.ok(menuClickRecordService.add(ccrMenuClickRecord));
    }


    @ApiOperation("一级菜单统计接口")
    @GetMapping("/center/click/menu/" + APIVersion.V1 + "/oneMenurecord")
    public MessageListBean<Object[]> oneMenurecord() {
        return MessageListBean.ok(menuClickRecordService.oneMenurecord());
    }


    @ApiOperation("应用菜单统计接口")
    @GetMapping("/center/click/menu/" + APIVersion.V1 + "/appMenurecord")
    public MessageListBean<Object[]> appMenurecord() {
        return MessageListBean.ok(menuClickRecordService.appMenurecord());
    }
}
