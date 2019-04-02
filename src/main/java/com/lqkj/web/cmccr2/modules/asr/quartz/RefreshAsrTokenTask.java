package com.lqkj.web.cmccr2.modules.asr.quartz;

import com.lqkj.web.cmccr2.modules.asr.service.BaiduAsrConfigService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;

/**
 * 刷新百度语音token
 * 定时任务
 */

public class RefreshAsrTokenTask extends QuartzJobBean {
    @Autowired
    BaiduAsrConfigService baiduAsrConfigService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            baiduAsrConfigService.refreshToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
