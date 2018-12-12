package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrIosApplication;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationCommonService;
import com.lqkj.web.cmccr2.modules.application.service.IOSApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "ios应用管理")
@RestController
public class IOSApplicationController {

    @Autowired
    ApplicationCommonService applicationCommonService;
    @Autowired
    IOSApplicationService iosApplicationService;

    @ApiOperation("创建ios应用")
    @PostMapping("/center/application/ios/" + APIVersion.V1 + "/create/")
    public MessageBean<Long> create(@ApiParam(value = "应用信息") CcrIosApplication application,
                                    @ApiParam(value = "图标文件") MultipartFile iconFile) throws Exception {
        application.setIconPath(applicationCommonService.saveUploadFile(iconFile, "png", "jpg"));
        return MessageBean.ok(iosApplicationService.createApplication(application));
    }

    @ApiOperation("更新ios应用")
    @PostMapping("/center/application/ios/" + APIVersion.V1 + "/update/")
    public MessageBean<Long> update(@RequestBody CcrIosApplication application) throws Exception {
        return MessageBean.ok(iosApplicationService.updateApplication(application));
    }

    @ApiOperation("删除ios应用")
    @DeleteMapping("/center/application/ios/" + APIVersion.V1 + "/delete/{id}/")
    public MessageBean<Long[]> delete(@ApiParam(value = "应用id") @PathVariable("id") Long[] iosId) {
        iosApplicationService.deleteApplication(iosId);
        return MessageBean.ok(iosId);
    }

    @ApiOperation("查询ios应用信息")
    @GetMapping("/center/application/ios/" + APIVersion.V1 + "/info/{id}/")
    public MessageBean<CcrIosApplication> info(@PathVariable("id") Long id) {
        return MessageBean.ok(iosApplicationService.getIOSApplicationById(id));
    }

    @ApiOperation("分页查询ios应用列表")
    @GetMapping("/center/application/ios/" + APIVersion.V1 + "/list")
    public Page<CcrIosApplication> list(Integer page, Integer pageSize) {
        return iosApplicationService.page(page, pageSize);
    }
}
