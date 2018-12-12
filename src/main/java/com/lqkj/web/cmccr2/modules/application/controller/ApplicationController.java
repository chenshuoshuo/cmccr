package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplication;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "pc应用管理")
@RestController
@Validated
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @ApiOperation("增加pc端应用")
    @PutMapping("/center/application/pc/" + APIVersion.V1 + "/create")
    public MessageBean<CcrApplication> add(CcrApplication application) {
        return MessageBean.ok(applicationService.add(application));
    }

    @ApiOperation("删除pc端应用")
    @DeleteMapping("/center/application/pc/" + APIVersion.V1 + "/delete/{id}")
    public MessageBean<Long> delete(@PathVariable("id") Long id) {
        applicationService.delete(id);
        return MessageBean.ok();
    }

    @ApiOperation("更新pc端应用")
    @PostMapping("/center/application/pc/" + APIVersion.V1 + "/update/{id}")
    public MessageBean<CcrApplication> update(@PathVariable Long id,
                                              @RequestBody CcrApplication application) {
        return MessageBean.ok(applicationService.update(id, application));
    }

    @ApiOperation("查询pc端应用信息")
    @GetMapping("/center/application/pc/" + APIVersion.V1 + "/{id}")
    public MessageBean<CcrApplication> info(@PathVariable Long id) {
        return MessageBean.ok(applicationService.info(id));
    }

    @ApiOperation("获取所有pc应用列表")
    @GetMapping("/center/application/pc/" + APIVersion.V1 + "/list")
    public MessageListBean<CcrApplication> list() {
        return MessageListBean.ok(applicationService.all());
    }
}
