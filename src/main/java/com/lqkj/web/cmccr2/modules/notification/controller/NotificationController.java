package com.lqkj.web.cmccr2.modules.notification.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.notification.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "消息通知")
@RestController
public class NotificationController {

    public static final String VERSION = "v1";

    @Autowired
    private NotificationService notificationService;

    @ApiOperation("创建消息通知")
    @PutMapping("/center/notification/" + VERSION + "/create")
    public MessageBean<CcrNotification> create(CcrNotification notification) {

        return MessageBean.ok(notificationService.add(notification));
    }

    @ApiOperation("删除消息")
    @ApiImplicitParam(name = "id", value = "消息id")
    @DeleteMapping("/center/notification/" + VERSION + "/delete/{id}")
    public MessageBean<Object> delete(@PathVariable("id") Integer id) throws Exception {
        notificationService.delete(id);
        return MessageBean.ok();
    }

    @ApiOperation("批量删除消息")
    @ApiImplicitParam(name = "ids", value = "消息ids字符串数组,多个以逗号隔开")
    @DeleteMapping("/center/notification/" + VERSION + "/batchDelete")
    public MessageBean<Object> delete(@RequestParam(name = "ids") String ids) throws Exception {
        notificationService.batchDelete(ids);
        return MessageBean.ok();
    }

//    @ApiOperation("更新菜单项")
//    @PostMapping("/center/menu/" + VERSION + "/update/{id}")
//    public MessageBean<CcrNotification> update(@PathVariable Long id,
//                                               @RequestBody CcrNotification CcrNotification) {
//        return MessageBean.ok(notificationService.update(id, CcrNotification));
//    }

//    @ApiOperation("搜索菜单项")
//    @GetMapping("/center/menu/" + VERSION + "/query")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "keyword", paramType = "query", value = "关键字")
//    })
//    public WebAsyncTask<MessageBean<Page<CcrNotification>>> search(String keyword, Integer page, Integer pageSize) {
//        return new WebAsyncTask<>(() -> MessageBean.ok(notificationService.page(keyword, page, pageSize)));
//    }

    @ApiOperation("PC端根据主键查询消息")
    @ApiImplicitParam(name = "id", value = "消息ID")
    @GetMapping("/center/notification/" + VERSION + "/infoForPC/{id}")
    public MessageBean<CcrNotification> infoForPC(@PathVariable("id") Integer id) {
        return  MessageBean.ok(notificationService.infoForPC(id));
    }

    @ApiOperation("H5页面根据主键查询消息")
    @ApiImplicitParam(name = "id", value = "消息ID")
    @GetMapping("/center/notification/" + VERSION + "/infoForH5/{id}{userCode}")
    public MessageBean<CcrNotification> infoForH5(@PathVariable("id") Integer id,@PathVariable("userCode") String userCode) {
        return  MessageBean.ok(notificationService.infoForH5(id,userCode));
    }

//    @ApiOperation("按照类型分页查询菜单列表")
//    @GetMapping(value = {"/center/menu/" + VERSION + "/page/{type}/",
//            "/center/menu/" + VERSION + "/page"})
//    public WebAsyncTask<MessageBean<Page<CcrNotification>>> typePage(@RequestParam Integer page,
//                                                                     @RequestParam Integer pageSize,
//                                                                     @RequestParam(required = false) String keyword,
//                                                                     @PathVariable(required = false) CcrNotification.IpsMenuType type) {
//        return new WebAsyncTask<>(() -> MessageBean.ok(notificationService.typePage(type, keyword, page, pageSize)));
//    }
}
