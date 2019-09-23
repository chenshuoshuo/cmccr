package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogVO;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.KeyValueVO;
import com.lqkj.web.cmccr2.modules.record.serivce.AccessLogRecordService;
import com.lqkj.web.cmccr2.modules.record.serivce.CcrAppRequestRecordService;
import com.lqkj.web.cmccr2.modules.record.serivce.KeyValueVOService;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import com.lqkj.web.cmccr2.utils.DateUtils;
import com.lqkj.web.cmccr2.utils.ServletUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "网关统计记录")
@RestController
@RequestMapping("/center/accessLog/record")
public class AccessLogRecordController {

    @Autowired
    AccessLogRecordService accessLogRecordService;
    @Autowired
    KeyValueVOService keyValueVOService;
    @Autowired
    CcrUserService userService;

    @ApiOperation("增加网关统计记录")
    @PutMapping(APIVersion.V1 + "/add")
    public MessageBean<CcrAccessLogRecord> addRecord(HttpServletRequest request,
                                                      Authentication authentication) {
        CcrAccessLogRecord accessLogRecord = new CcrAccessLogRecord();
        Timestamp logTime = new Timestamp(new Date().getTime());
        accessLogRecord.setIpAddress(ServletUtils.getIpAddress(request));
        accessLogRecord.setLogTime(logTime);
        if(authentication == null){
            accessLogRecord.setUserId("guest");
            accessLogRecord.setUserGroup("guest");
        } else {
            Jwt jwt =(Jwt)authentication.getPrincipal();
            String userCode = (String)jwt.getClaims().get("user_name");
            accessLogRecord.setUserId(userCode);
            CcrUser ccrUser = userService.findByUserCode(userCode);
            if(ccrUser != null){
                if(ccrUser.getUserGroup() == null){
                    accessLogRecord.setUserGroup("manager");
                } else {
                    accessLogRecord.setUserGroup(ccrUser.getUserGroup().toString());
                }
            }
        }
        return MessageBean.ok(accessLogRecordService.add(accessLogRecord));
    }
    /**
     * 删除指定时间段访问记录
     */
    @ApiOperation("删除指定时间段访问记录")
    @GetMapping(APIVersion.V1 +"/delete")
    @ResponseBody
    public MessageBean delete(@RequestParam String startDate,
                              @RequestParam String endDate){
        accessLogRecordService.deleteAllByLogTime(startDate,endDate);
        List<CcrAccessLogRecord> list = accessLogRecordService.listQuery(startDate,endDate);
        if(list.size() > 0){
           return MessageBean.error("删除失败");
        }else {
            return MessageBean.ok("删除成功");
        }
    }
    /**
     * 综合分析
     * @return
     */

    @ApiOperation("综合分析")
    @GetMapping(APIVersion.V1 +"/statistic")
    @ResponseBody
    public MessageBean<CcrAccessLogVO> statistic() {
        MessageBean<CcrAccessLogVO> messageBean = new MessageBean<CcrAccessLogVO>(null);
        try {
            CcrAccessLogVO ccrAccessLogVO = new CcrAccessLogVO();


            // 当周使用数
            String[] weekTimeArray = DateUtils.getDefaultInstance().getThisWeekArray();
            ccrAccessLogVO.setWeekUseCount(accessLogRecordService.useCount(weekTimeArray[0],weekTimeArray[1]));

            // 当月使用数
            String[] monthTimeArray = DateUtils.getDefaultInstance().getThisMonthArray();
            ccrAccessLogVO.setMonthUseCount(accessLogRecordService.useCount(monthTimeArray[0], monthTimeArray[1]));

            //当年使用总数
            String[] yearTimeArray = DateUtils.getDefaultInstance().getThisYearArray();
            ccrAccessLogVO.setThisYearUseCount(accessLogRecordService.useCount(yearTimeArray[0], yearTimeArray[1]));

            //ip总数
            ccrAccessLogVO.setIpCount(accessLogRecordService.ipCount());

            //pv总数
            ccrAccessLogVO.setPvCount(accessLogRecordService.useCountAll());


            List<KeyValueVO> list = new ArrayList<>();
            //使用总数
            Integer countAll = accessLogRecordService.useCountAll();
            KeyValueVO useAllCount = new KeyValueVO("使用总数",countAll.toString());

            //用户组使用统计
            List<KeyValueVO> useGroupCount = keyValueVOService.userGroupCount();
            //useGroupCount.add(useAllCount);
            ccrAccessLogVO.setUserGroupCountList(useGroupCount);
            return MessageBean.ok(ccrAccessLogVO);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageBean.error(e.getMessage());
        }
    }
}
