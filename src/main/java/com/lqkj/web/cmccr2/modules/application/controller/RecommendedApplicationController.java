package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrRecommendedApplication;
import com.lqkj.web.cmccr2.modules.application.service.RecommendedApplicationService;
import com.lqkj.web.cmccr2.utils.UUIDUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "推荐应用管理")
@RestController
public class RecommendedApplicationController {

    @Autowired
    public RecommendedApplicationService applicationService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName",  value = "应用名称"),
            @ApiImplicitParam(name = "appUrl",  value = "应用路径"),
            @ApiImplicitParam(name = "appLogo",  value = "应用Logo"),
            @ApiImplicitParam(name = "startTime",  value = "推荐起始时间"),
            @ApiImplicitParam(name = "endTime",  value = "推荐结束时间"),
            @ApiImplicitParam(name = "orderId",  value = "排序"),
            @ApiImplicitParam(name = "memo",  value = "备注")
    })
    @ApiOperation("创建推荐应用")
    @PostMapping("/center/application/recommend/" + APIVersion.V1 + "/create/")
    public MessageBean<CcrRecommendedApplication> create( String appName,
                                                          String appUrl,
                                                          String appLogo,
                                                          String startTime,
                                                          String endTime,
                                                          Integer orderId,
                                                          String memo) throws Exception {
        String appId = UUIDUtils.getUUID();
        Timestamp start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(startTime).getTime());
        Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(endTime).getTime());
        CcrRecommendedApplication application = new CcrRecommendedApplication();
        List<CcrRecommendedApplication> list = applicationService.queryAllList();
        if(list.size() > 0){
            if(application.getOrderId() == null){
                for(int i = 0; i < list.size();i++){
                    list.get(i).setOrderId(i+1);
                    applicationService.createRecommendedApplication(list.get(i));
                }
                application.setOrderId(list.size()+1);
            }
            if(1 == application.getOrderId()){
                for(int i = 0; i < list.size();i++){
                    list.get(i).setOrderId(i+2);
                    applicationService.createRecommendedApplication(list.get(i));
                }
            }
            if(application.getOrderId() > 1 && application.getOrderId() <= list.size()){
                for(int i = 0; i < application.getOrderId()-1;i++){
                    list.get(i).setOrderId(i+1);
                    applicationService.createRecommendedApplication(list.get(i));
                }
                for(int i = application.getOrderId()-1; i < list.size();i++){
                    list.get(i).setOrderId(i+2);
                    applicationService.createRecommendedApplication(list.get(i));
                }
            }
            if(application.getOrderId() > list.size()){
                for(int i = 0; i < list.size();i++){
                    list.get(i).setOrderId(i+1);
                    applicationService.createRecommendedApplication(list.get(i));
                }
                application.setOrderId(list.size()+1);
            }
        }else{
            application.setOrderId(1);
        }
        application.setMemo(memo);
        application.setAppName(appName);
        application.setAppLogo(appLogo);
        application.setStartTime(start);
        application.setEndTime(end);
        application.setAppId(appId);
        application.setAppUrl(appUrl);
        return MessageBean.ok(applicationService.createRecommendedApplication(application));
    }

    @ApiOperation("批量创建推荐应用")
    @PostMapping("/center/application/recommend/" + APIVersion.V1 + "/createBatch/")
    public MessageBean<List<CcrRecommendedApplication>> create(@ApiParam(value = "应用信息") @RequestBody List<CcrRecommendedApplication> applicationList) throws Exception {
        List<CcrRecommendedApplication> list = applicationService.queryAllList();
        for(CcrRecommendedApplication application:applicationList){
            String appId = UUIDUtils.getUUID();
            application.setAppId(appId);
            if(list.size() > 0){
                if(application.getOrderId() == null){
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+1);
                        applicationService.createRecommendedApplication(list.get(i));
                    }
                    application.setOrderId(list.size()+1);
                }
                if(1 == application.getOrderId()){
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+2);
                        applicationService.createRecommendedApplication(list.get(i));
                    }
                }
                if(application.getOrderId() > 1 && application.getOrderId() <= list.size()){
                    for(int i = 0; i < application.getOrderId()-1;i++){
                        list.get(i).setOrderId(i+1);
                        applicationService.createRecommendedApplication(list.get(i));
                    }
                    for(int i = application.getOrderId()-1; i < list.size();i++){
                        list.get(i).setOrderId(i+2);
                        applicationService.createRecommendedApplication(list.get(i));
                    }
                }
                if(application.getOrderId() > list.size()){
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+1);
                        applicationService.createRecommendedApplication(list.get(i));
                    }
                    application.setOrderId(list.size()+1);
                }
            }else{
                application.setOrderId(1);
            }
        }
        return MessageBean.ok(applicationService.createBatchRecommendedApplication(applicationList));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "应用id"),
            @ApiImplicitParam(name = "appName",  value = "应用名称"),
            @ApiImplicitParam(name = "appUrl",  value = "应用路径"),
            @ApiImplicitParam(name = "appLogo",  value = "应用Logo"),
            @ApiImplicitParam(name = "startTime",  value = "推荐起始时间"),
            @ApiImplicitParam(name = "endTime",  value = "推荐结束时间"),
            @ApiImplicitParam(name = "orderId",  value = "排序"),
            @ApiImplicitParam(name = "memo",  value = "备注")
    })
    @ApiOperation("更新推荐应用")
    @PostMapping("/center/application/recommend/" + APIVersion.V1 + "/update/{id}")
    public MessageBean<CcrRecommendedApplication> update(@PathVariable(name = "id") String id,
                                       String appName,
                                       String appUrl,
                                       String appLogo,
                                       String startTime,
                                       String endTime,
                                       Integer orderId,
                                       String memo) throws Exception {

        Timestamp start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(startTime).getTime());
        Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(endTime).getTime());
        return MessageBean.ok(applicationService.updateRecommendedApplication(id, appName, appUrl,
                appLogo, start, end,orderId,memo));
    }

    @ApiOperation("删除推荐应用")
    @DeleteMapping("/center/application/recommend/" + APIVersion.V1 + "/delete/{id}/")
    public MessageBean<String[]> delete(@ApiParam(value = "应用id") @PathVariable("id") String[] appId) throws IOException {
        applicationService.deleteRecommendedApplication(appId);
        return MessageBean.ok(appId);
    }

    @ApiOperation("查询推荐应用信息")
    @GetMapping("/center/application/recommend/" + APIVersion.V1 + "/info/{id}/")
    public MessageBean<CcrRecommendedApplication> info(@PathVariable("id") String id) {
        return MessageBean.ok(applicationService.getRecommendedApplicationById(id));
    }

    @ApiOperation("查询推荐应用列表")
    @GetMapping("/center/application/recommend/" + APIVersion.V1 + "/list")
    public List<CcrRecommendedApplication> list() throws Exception{

        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH");
        String time = sdf.format(new Date());
        Timestamp systemTime = new Timestamp(sdf.parse(time).getTime());
        return applicationService.queryList(systemTime);
    }

}
