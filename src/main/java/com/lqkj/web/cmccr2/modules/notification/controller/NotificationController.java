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

import java.util.List;

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
    public MessageBean<Object> delete(@PathVariable(name = "id") Integer id) throws Exception {
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


    @ApiOperation("PC端根据主键查询消息")
    @ApiImplicitParam(name = "id", value = "消息ID")
    @GetMapping("/center/notification/" + VERSION + "/infoForPC/{id}")
    public MessageBean<CcrNotification> infoForPC(@PathVariable(name = "id") Integer id) {
        return  MessageBean.ok(notificationService.infoForPC(id));
    }

    @ApiOperation("H5页面根据主键查询消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息ID"),
            @ApiImplicitParam(name = "userCode", value = "用户CODE")
    })
    @GetMapping("/center/notification/" + VERSION + "/infoForH5/{id}/{userCode}")
    public MessageBean<CcrNotification> infoForH5(@PathVariable(name = "id") Integer id,@PathVariable(name = "userCode") String userCode) {
        return  MessageBean.ok(notificationService.infoForH5(id,userCode));
    }

    @ApiOperation("按照标题和权限分页查询")
    @GetMapping("/center/notification/" + VERSION + "/page")
    public MessageBean<Page<CcrNotification>> pageQuery(@RequestParam Integer page,
                                                                     @RequestParam Integer pageSize,
                                                                     @RequestParam(required = false) String title,
                                                                     @RequestParam(required = true) String auth) {
        Page<CcrNotification> pageList = notificationService.page(title, auth, page, pageSize);
//        List<CcrNotification> list = pageList.getContent();
//        if(list.size()>0){
//            for(CcrNotification notification:list){
//                if(notification.getTargetUserRole().toString().contains("游客")){
//                    notification.setAuth("游客");
//                }
//                if(notification.getSpecifyUserId() != null){
//                    notification.setAuth("指定用户");
//                }
//            }
//        }
        //System.out.println(list);
        return MessageBean.ok(pageList);
    }
}
