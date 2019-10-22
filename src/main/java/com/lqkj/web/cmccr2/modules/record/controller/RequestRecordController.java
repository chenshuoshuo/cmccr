package com.lqkj.web.cmccr2.modules.record.controller;

import com.ecwid.consul.v1.health.model.Check;
import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrLocationRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrStatisticsFrequency;
import com.lqkj.web.cmccr2.modules.record.serivce.MapSearchServiceApi;
import com.lqkj.web.cmccr2.modules.record.serivce.RequestRecordService;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import com.lqkj.web.cmccr2.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "网关统计")
@RestController
public class RequestRecordController {

    private RequestRecordService requestRecordService;

    private MapSearchServiceApi searchServiceApi;

    @Autowired
    CcrUserService ccrUserService;

    @Value("${cmgis.context-path}")
    private String cmgisContextPath;

    public RequestRecordController(RequestRecordService requestRecordService) {
        this.requestRecordService = requestRecordService;
    }

    @ApiOperation("增加请求记录")
    @PutMapping("/center/record/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(CcrRequestRecord requestRecord,
                                        HttpServletRequest request,
                                        Authentication authentication) {
        return new WebAsyncTask<>(() -> {
            requestRecord.setIp(ServletUtils.getIpAddress(request));

            if(authentication == null){
                requestRecord.setUserGroup("guest");
            } else {
                Jwt jwt = (Jwt)authentication.getPrincipal();
                if(jwt.getClaims().get("user_name")==null){
                    requestRecord.setUserGroup("guest");
                }else {
                    String userCode = (String)jwt.getClaims().get("user_name");
                    requestRecord.setUserCode(userCode);
                    CcrUser ccrUser = ccrUserService.findByUserCode(userCode);
                    if(ccrUser != null){
                        if(ccrUser.getUserGroup() == null){
                            requestRecord.setUserGroup("manager");
                        } else {
                            requestRecord.setUserGroup(ccrUser.getUserGroup().toString());
                        }
                    }else {
                        requestRecord.setUserGroup("manager");
                    }
                }
            }
            requestRecordService.add(requestRecord);
            return null;
        });
    }

    @ApiOperation("查询app访问统计")
    @GetMapping("/center/record/" + APIVersion.V1 + "/app")
    public MessageBean<List<Object[]>> appRecord() {
        return MessageBean.ok(requestRecordService.appRecord());
    }

    @ApiOperation("查询数据统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/data")
    public MessageBean<List<Object[]>> dataRecord(@RequestParam Timestamp startTime,
                                                  @RequestParam Timestamp endTime,
                                                  @RequestParam CcrStatisticsFrequency frequencyEnum,
                                                  @RequestParam Boolean successed) {
        return MessageBean.ok(requestRecordService.dataStatistics(startTime, endTime, frequencyEnum, successed));
    }

    @ApiOperation("查询流量统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/url")
    public MessageBean<Page<Object[]>> urlRecord(@RequestParam Timestamp startTime,
                                                 @RequestParam Timestamp endTime,
                                                 @RequestParam Integer page,
                                                 @RequestParam Integer pageSize) {
        return MessageBean.ok(requestRecordService.urlStatistics(startTime, endTime, page, pageSize));
    }

    @ApiOperation("查询详细流量统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/url/detail")
    public MessageListBean<Object[]> urlRecordDetail(Timestamp startTime, Timestamp endTime, String name) {
        return MessageListBean.ok(requestRecordService.urlRecordDetail(startTime, endTime, name));
    }

    @ApiOperation("查询地理统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/location")
    public MessageListBean<CcrLocationRecord> locationRecord(@RequestParam Timestamp startTime,
                                                             @RequestParam Timestamp endTime) {
        return MessageListBean.ok(requestRecordService.locationStatistics(startTime, endTime));
    }

    @GetMapping("/center/sys/log/" + APIVersion.V1 + "/location/export")
    @ApiOperation("导出地理统计结果")
    public ResponseEntity<StreamingResponseBody> export(@RequestParam Timestamp startTime,
                                                        @RequestParam Timestamp endTime) {
        StreamingResponseBody body = outputStream -> {
            requestRecordService.exportLocationStatistics(startTime, endTime, outputStream);
        };

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=location.xlsx")
                .body(body);
    }

    @ApiOperation("查询错误统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/error")
    public MessageBean<Page<CcrRequestRecord>> errorRecord(@RequestParam Timestamp startTime,
                                                           @RequestParam Timestamp endTime,
                                                           @RequestParam Integer page,
                                                           @RequestParam Integer pageSize) {
        return MessageBean.ok(requestRecordService.errorRecord(startTime, endTime,
                page, pageSize));
    }

    @ApiOperation("查询系统状态统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/system")
    public MessageBean<Map<String, List<Check.CheckStatus>>> systemRecord() {
        return MessageBean.ok(requestRecordService.systemRecord());
    }

    @ApiOperation("查询ip访问统计结果")
    @GetMapping("/center/record/" + APIVersion.V1 + "/ip")
    public MessageBean<Integer> ipRecord() {
        return MessageBean.ok(requestRecordService.ipRecord());
    }

    @ApiOperation("查询地图搜索统计")
    @GetMapping("/center/record/" + APIVersion.V1 + "/map/search")
    public MessageBean<Object[]> searchRecord() {
        return searchServiceApi.record(cmgisContextPath);
    }

    @ApiOperation("查询用户组访问统计")
    @GetMapping("/center/record/" + APIVersion.V1 + "/userGroup")
    public MessageBean<List<Object[]>> userGroupStatistic() {
        return MessageBean.ok(requestRecordService.userGroupStatistic());
    }

    public MapSearchServiceApi getSearchServiceApi() {
        return searchServiceApi;
    }

    @Autowired(required = false)
    public void setSearchServiceApi(MapSearchServiceApi searchServiceApi) {
        this.searchServiceApi = searchServiceApi;
    }

}
