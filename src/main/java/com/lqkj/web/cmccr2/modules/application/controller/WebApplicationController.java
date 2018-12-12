package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationCommonService;
import com.lqkj.web.cmccr2.modules.application.service.WebApplicationService;
import com.lqkj.web.cmccr2.modules.application.domain.CcrWebApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "web应用管理")
@RestController
public class WebApplicationController {

    public static final String VERSION = "v1";

    @Autowired
    public ApplicationCommonService applicationCommonService;
    @Autowired
    public WebApplicationService webApplicationService;

    @ApiOperation("创建web应用")
    @PostMapping("/center/application/web/" + VERSION + "/create/")
    public MessageBean<Long> create(@ApiParam(value = "应用信息") CcrWebApplication application,
                                    @ApiParam(value = "图标文件") MultipartFile iconFile) throws Exception {
        application.setIconPath(applicationCommonService.saveUploadFile(iconFile,"png","jpg"));
        return MessageBean.ok(webApplicationService.createApplication(application));
    }

    @ApiOperation("更新web应用")
    @PostMapping("/center/application/web/" + VERSION + "/update/")
    public MessageBean<Long> update(@ApiParam(value = "应用信息") CcrWebApplication application) {

        return MessageBean.ok(webApplicationService.updateApplication(application));
    }

    @ApiOperation("删除web应用")
    @DeleteMapping("/center/application/web/" + VERSION + "/delete/{id}/")
    public MessageBean<Long[]> delete(@ApiParam(value = "应用id") @PathVariable("id") Long[] webId) {
        webApplicationService.deleteApplication(webId);
        return MessageBean.ok(webId);
    }

    @ApiOperation("查询web应用信息")
    @GetMapping("/center/application/web/" + VERSION + "/info/{id}/")
    public MessageBean<CcrWebApplication> info(@PathVariable("id") Long id) {
        return MessageBean.ok(webApplicationService.getApplicationById(id));
    }

    @ApiOperation("分页查询web应用信息")
    @GetMapping("/center/application/web/" + VERSION + "/page/")
    public Page<CcrWebApplication> page(Integer page, Integer pageSize) {
        return webApplicationService.getWebApplicationPage(page, pageSize);
    }
}
