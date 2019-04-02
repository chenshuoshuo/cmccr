package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.modules.asr.quartz.RefreshAsrTokenTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 * @version 1.0
 * @author RY
 */

@Configuration
public class QuartzConfig {

    /**
     * 注册定时任务类bean
     * @return
     */
    @Bean
    public JobDetail refreshAsrTokenTask(){
        return JobBuilder.newJob(RefreshAsrTokenTask.class)
                .withIdentity("refreshAsrToken")
                .storeDurably()
                .build();
    }

    /**
     * 注册表达式bean
     * @return
     */
    @Bean
    public Trigger refreshAsrTokenTaskTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withMisfireHandlingInstructionFireNow()
                .withIntervalInHours(24 * 20)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(refreshAsrTokenTask())
                .withIdentity("refreshAsrToken")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
