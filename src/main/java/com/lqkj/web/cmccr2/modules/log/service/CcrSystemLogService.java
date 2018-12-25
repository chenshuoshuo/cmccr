package com.lqkj.web.cmccr2.modules.log.service;

import com.lqkj.web.cmccr2.modules.log.dao.CcrSystemLogRepository;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CcrSystemLogService {

    @Autowired
    CcrSystemLogRepository systemLogRepository;

    /**
     * 增加一条日志记录
     */
    public void addLog(String source, String method, String description) {
        CcrSystemLog systemLog = new CcrSystemLog(source, method, description);
        systemLog.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        systemLogRepository.save(systemLog);
    }

    /**
     * 分页
     */
    public Page<CcrSystemLog> page(Timestamp startTime, Timestamp endTime,
                                   Integer page, Integer pageSize) {
        if (startTime!=null && endTime!=null) {
            return systemLogRepository.pageByTime(startTime, endTime, PageRequest.of(page, pageSize));
        }

        return systemLogRepository.findAll(PageRequest.of(page, pageSize));
    }


}
