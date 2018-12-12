package com.lqkj.web.cmccr2.modules.log.service;

import com.lqkj.web.cmccr2.modules.log.dao.CcrSystemLogRepository;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CcrSystemLogService {

    @Autowired
    CcrSystemLogRepository systemLogRepository;

    /**
     * 增加一条日志记录
     */
    public void addLog(String source, String method, String description) {
        systemLogRepository.save(new CcrSystemLog(source, method, description));
    }
}
