package com.lqkj.web.cmccr2.modules.request.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrStatisticsFrequency;
import com.lqkj.web.cmccr2.modules.request.serivce.CcrRequestRecordService;
import com.lqkj.web.cmccr2.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Api(tags = "网关统计")
@RestController
public class CcrRequestRecordController {

    @Autowired
    CcrRequestRecordService requestRecordService;

    @ApiOperation("增加请求记录")
    @PutMapping("/center/record/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(CcrRequestRecord requestRecord,
                                        HttpServletRequest request) {
        return new WebAsyncTask<>(() -> {
            requestRecord.setIp(ServletUtils.createBaseUrl(request));
            requestRecordService.add(requestRecord);
            return null;
        });
    }

    @ApiOperation("查询数据统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/data")
    public MessageBean<List<Object[]>> dataRecord(@RequestParam Timestamp startTime,
                                                  @RequestParam Timestamp endTime,
                                                  @RequestParam CcrStatisticsFrequency frequencyEnum,
                                                  @RequestParam Boolean successed) {
        return MessageBean.ok(requestRecordService.dataStatistics(startTime, endTime, frequencyEnum, successed));
    }
}
