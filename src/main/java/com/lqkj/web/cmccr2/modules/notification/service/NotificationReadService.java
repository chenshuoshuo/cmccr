package com.lqkj.web.cmccr2.modules.notification.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrNotificationReadRepository;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrNotificationRepository;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotificationRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 系统通知
 */
@Service
@Transactional
public class NotificationReadService {

    @Autowired
    CcrNotificationReadRepository readRepository;

    @Autowired
    CcrSystemLogService systemLogService;


    public CcrNotificationRead add(CcrNotificationRead read) {
        systemLogService.addLog("消息已读记录", "add",
                "增加消息已读记录");

        return readRepository.save(read);
    }

}
