package com.lqkj.web.cmccr2.modules.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBaseBean;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.application.domain.CcrMultiApplication;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationCommonService;
import com.lqkj.web.cmccr2.modules.application.service.MultiApplicationService;
import com.lqkj.web.cmccr2.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = {"组合应用"}, description = "组合应用管理")
@RestController
public class MultiApplicationController {

    @Autowired
    MultiApplicationService multiApplicationService;

    @Autowired
    ApplicationCommonService applicationCommonService;

    @Autowired
    ObjectMapper objectMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "application", type = "form", defaultValue = "应用信息")
    })
    @ApiOperation("创建组合应用")
    @PostMapping(value = "/center/application/multi/" + APIVersion.V1 + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageBean<Long> create(@RequestParam String application,
                                    @RequestParam MultipartFile iconFile) throws Exception {
        String iconPath = applicationCommonService.saveUploadFile(iconFile, "png", "jpg");

        return MessageBean.ok(multiApplicationService.createApplication(objectMapper.readValue(application,
                CcrMultiApplication.class), iconPath));
    }

    @ApiOperation("删除组合应用")
    @DeleteMapping("/center/application/multi/" + APIVersion.V1 + "/delete/{id}")
    public MessageBaseBean delete(@PathVariable(name = "id") Long[] id) {
        multiApplicationService.deleteApplication(id);

        return MessageBean.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "组合应用id", required = true, paramType = "path")
    })
    @ApiOperation("更新组合应用")
    @PostMapping("/center/application/multi/" + APIVersion.V1 + "/update/{id}")
    public MessageBean<Long> update(@RequestParam String application,
                                    @RequestParam(required = false) MultipartFile iconFile,
                                    @PathVariable Long id) throws Exception {
        CcrMultiApplication multiApplication = objectMapper.readValue(application,
                CcrMultiApplication.class);

        if (iconFile!=null) {
            String iconPath = applicationCommonService.saveUploadFile(iconFile, "png", "jpg");
            multiApplication.setIconPath(iconPath);
        }

        return MessageBean.ok(multiApplicationService.updateApplication(id, multiApplication));
    }

    @ApiOperation("查询应用信息")
    @GetMapping("/center/application/multi/" + APIVersion.V1 + "/info/{id}")
    public MessageBean<CcrMultiApplication> info(@PathVariable(name = "id") Long id) {
        return MessageBean.ok(multiApplicationService.getApplication(id));
    }

    @ApiOperation("根据应用id获取二维码")
    @GetMapping("/center/application/multi/" + APIVersion.V1 + "/qrcode/{id}")
    public MessageBean<String> qrcode(@PathVariable Long id,
                                      HttpServletRequest request) throws Exception {

        return MessageBean.ok(multiApplicationService.createAppQRCode(id, ServletUtils.createBaseUrl(request)));
    }

    @ApiOperation("根据系统类型在线下载应用")
    @GetMapping("/center/application/multi/" + APIVersion.V1 + "/jump/{id}")
    public void jump(@PathVariable(name = "id") Long id,
                     @ApiIgnore @RequestHeader(name = "User-Agent") String userAgent,
                     @ApiIgnore HttpServletResponse response) throws IOException {
        CcrMultiApplication application = multiApplicationService.getApplication(id);
        //android系统
        if (userAgent.contains("Android")) {
            String androidURL = application.getAndroidURL();

            if (androidURL!=null) {
                response.sendRedirect(androidURL);
                return;
            }
        }
        //IOS手机
        if (userAgent.contains("iPhone")) {
            String iosURL = application.getIosURL();

            if (iosURL!=null) {
                response.sendRedirect(iosURL);
                return;
            }
        }
        //web浏览器
        String webURL = application.getWebURL();

        if (webURL!=null) {
            response.sendRedirect(webURL);
        }

        this.applicationCommonService.countPlusOne(id);
    }

    @ApiOperation("分页查询组合应用列表")
    @GetMapping("/center/application/multi/" + APIVersion.V1 + "/page")
    public MessageBean<Page<CcrMultiApplication>> page(@RequestParam(required = false) String keyword,
                                                       @RequestParam Integer page,
                                                       @RequestParam Integer pageSize) {
        return MessageBean.ok(multiApplicationService.getPage(keyword, page, pageSize));
    }

    @ApiOperation("测试二维码生成")
    @GetMapping(value = "/center/application/multi/" + APIVersion.V1 + "/qrcode/download/simple.png")
    public ResponseEntity<StreamingResponseBody> downloadSimpleQRCode() {
        StreamingResponseBody body = outputStream -> {
            try {
                multiApplicationService.createSimpleQRCode(outputStream);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        };

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(body);
    }
}
