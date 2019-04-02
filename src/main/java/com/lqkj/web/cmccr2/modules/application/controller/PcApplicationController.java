package com.lqkj.web.cmccr2.modules.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrPcApplication;
import com.lqkj.web.cmccr2.modules.application.service.PcApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.io.IOException;

@Api(tags = "pc应用管理")
@RestController
public class PcApplicationController {

    @Autowired
    PcApplicationService pcApplicationService;

    @Autowired
    ObjectMapper objectMapper;

    @ApiOperation("增加pc端应用")
    @PutMapping("/center/application/pc/" + APIVersion.V1 + "/create")
    public String add(@RequestBody String application) throws Exception {
        return objectMapper.writeValueAsString(MessageBean.ok(pcApplicationService.add(objectMapper.readValue(application,
                CcrPcApplication.class))));
    }

    @ApiOperation("删除pc端应用")
    @DeleteMapping("/center/application/pc/" + APIVersion.V1 + "/delete/{id}")
    public MessageBean<Long> delete(@PathVariable("id") Long id) {
        pcApplicationService.delete(id);
        return MessageBean.ok();
    }

    @ApiOperation("更新pc端应用")
    @PostMapping("/center/application/pc/" + APIVersion.V1 + "/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestBody String application) throws IOException {
        return objectMapper.writeValueAsString(MessageBean.ok(pcApplicationService.update(id,
                objectMapper.readValue(application, CcrPcApplication.class))));
    }

    @ApiOperation("查询pc端应用信息")
    @GetMapping("/center/application/pc/" + APIVersion.V1 + "/{id}")
    public WebAsyncTask<String> info(@PathVariable Long id) throws JsonProcessingException {
        return new WebAsyncTask<>(() -> objectMapper.writeValueAsString(MessageBean.ok(pcApplicationService.info(id))));
    }

    @ApiOperation("获取所有pc应用列表")
    @GetMapping("/center/application/pc/" + APIVersion.V1 + "/list")
    public WebAsyncTask<MessageListBean<CcrPcApplication>> list() {
        return new WebAsyncTask<>(() -> MessageListBean.ok(pcApplicationService.all()));
    }

    @ApiOperation("根据菜单id获取应用列表")
    @GetMapping("/center/application/pc/" + APIVersion.V1 + "/menu/{menuId}/list")
    public WebAsyncTask<MessageListBean<CcrPcApplication>> findByMenuId(@PathVariable("menuId") Long menuId) {
        return new WebAsyncTask<>(() -> MessageListBean.ok(pcApplicationService.findByParentId(menuId)));
    }
}
