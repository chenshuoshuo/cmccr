package com.lqkj.web.cmccr2.modules.application.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.modules.application.domain.CcrVersionApplication;
import com.lqkj.web.cmccr2.modules.application.service.ApplicationCommonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * 应用搜索
 */
@Api(tags = "应用搜索")
@RestController
public class SearchApplicationController {

    @Autowired
    ApplicationCommonService applicationCommonService;

    @ApiOperation("按照名称搜索应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", paramType = "query"),
            @ApiImplicitParam(name = "type", paramType = "query", allowableValues = "ios,android,web")
    })
    @GetMapping("/center/application/search/" + APIVersion.V1 + "/")
    public WebAsyncTask<Page<CcrVersionApplication>> search(@RequestParam(required = false) String keyword,
                                                            @RequestParam String type,
                                                            @RequestParam(required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return new WebAsyncTask<>(() -> applicationCommonService.search(keyword, type, page, pageSize));
    }
}
