package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import com.lqkj.web.cmccr2.modules.application.service.AndroidApplicationService;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationCommonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

@Api(tags = "android应用管理")
@RestController
public class AndroidApplicationController {

    @Autowired
    public ApplicationCommonService applicationCommonService;

    @Autowired
    public AndroidApplicationService androidApplicationService;

    @ApiOperation("创建android应用")
    @PostMapping("/center/application/android/" + APIVersion.V1 + "/create/")
    public MessageBean<Long> create(@ApiParam(value = "应用信息") CcrAndroidApplication application,
                                    @ApiParam(value = "apk文件") MultipartFile apkFile,
                                    @ApiParam(value = "图标文件") MultipartFile iconFile) throws Exception {
        application.setApkPath(applicationCommonService.saveUploadFile(apkFile, "apk"));
        application.setIconPath(applicationCommonService.saveUploadFile(iconFile, "png", "jpg"));

        return MessageBean.ok(androidApplicationService.createAndroidApplication(application));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", value = "应用id"),
            @ApiImplicitParam(name = "versionCode", paramType = "form", value = "版本号"),
            @ApiImplicitParam(name = "versionName", paramType = "form", value = "版本名称"),
            @ApiImplicitParam(name = "updateDescription", paramType = "form", value = "更新说明")
    })
    @ApiOperation("更新android应用")
    @PostMapping("/center/application/android/" + APIVersion.V1 + "/update/{id}")
    public MessageBean<Integer> update(@PathVariable(name = "id") Long id,
                                       Integer versionCode,
                                       String versionName,
                                       String updateDescription,
                                       MultipartFile apkFile) throws Exception {
        String applicationPath = applicationCommonService.saveUploadFile(apkFile, "apk");
        androidApplicationService.updateAndroidApplication(id, versionCode, versionName,
                updateDescription, applicationPath);
        return MessageBean.ok(versionCode);
    }

    @ApiOperation("删除android应用")
    @DeleteMapping("/center/application/android/" + APIVersion.V1 + "/delete/{id}/")
    public MessageBean<Long[]> delete(@ApiParam(value = "应用id") @PathVariable("id") Long[] androidId) throws IOException {
        androidApplicationService.deleteAndroidApplication(androidId);
        return MessageBean.ok(androidId);
    }

    @ApiOperation("查询android应用信息")
    @GetMapping("/center/application/android/" + APIVersion.V1 + "/info/{id}/")
    public MessageBean<CcrAndroidApplication> info(@PathVariable("id") Long id) {
        return MessageBean.ok(androidApplicationService.getApplicationById(id));
    }

    @ApiOperation("分页查询android应用列表")
    @GetMapping("/center/application/android/" + APIVersion.V1 + "/list")
    public Page<CcrAndroidApplication> list(Integer page,
                                            Integer pageSize) {
        return androidApplicationService.page(page, pageSize);
    }

    @ApiOperation("下载android应用")
    @ApiResponse(code = 200, message = "文件下载成功", responseHeaders = {
            @ResponseHeader(name = "Content-Type", description = "application/octet-stream"),
            @ResponseHeader(name = "Content-Disposition", description = "attachment")
    })
    @GetMapping("/center/application/android/" + APIVersion.V1 + "/download/{id}/")
    public ResponseEntity<StreamingResponseBody> download(@ApiParam(value = "应用id") @PathVariable("id") Long id) {
        StreamingResponseBody body = outputStream -> {
            androidApplicationService.readAndroidStream(id, outputStream);
        };
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename="
                        + androidApplicationService.getApkFileName(id))
                .body(body);
    }

    @GetMapping("/center/application/android/" + APIVersion.V1 + "/check/{id}")
    public MessageBean<Boolean> checkUpdate(@PathVariable("id") Long id,
                                            @RequestParam("versionCode") Integer versionCode) {
        return null;
    }
}
