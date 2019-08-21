package com.lqkj.web.cmccr2.modules.notification.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsers;
import com.lqkj.web.cmccr2.modules.application.domain.CcrPcApplication;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrNotificationRepository;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotificationRead;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统通知
 */
@Service
@Transactional
public class NotificationService {

    @Autowired
    CcrNotificationRepository notificationRepository;

    @Autowired
    CcrSystemLogService systemLogService;


    public CcrNotification add(CcrNotification notification) {
        systemLogService.addLog("消息通知", "add",
                "增加消息通知配置");

        return notificationRepository.save(notification);
    }

    public void delete(Integer infoId) {
        systemLogService.addLog("消息通知", "delete",
                "删除消息通知");

        notificationRepository.deleteById(infoId);
    }

    public void batchDelete(String ids) {
        systemLogService.addLog("消息通知", "batchDelete",
                "批量删除消息通知");
        Integer[] idArray = Arrays
                .stream(ids.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        for(Integer id:idArray){
            notificationRepository.deleteById(id);
        }
    }

//    public CcrNotification update(Integer id, CcrNotification notification) {
//        systemLogService.addLog("消息通知", "update",
//                "更新消息通知配置");
//
//        CcrNotification savedApp = notificationRepository.getOne(id);
//
//        return applicationRepository.save(savedApp);
//    }

    /**
     * H5根据主键查询
     * @param id
     * @return
     */
    public CcrNotification infoForH5(Integer id,String userCode) {
        systemLogService.addLog("消息通知", "infoForH5",
                "查询消息通知");
        Timestamp readTime = new Timestamp(new Date().getTime());
        CcrNotificationRead notificationRead = new CcrNotificationRead();
        notificationRead.setInfoId(id);
        notificationRead.setUserCode(userCode);
        notificationRead.setReadTime(readTime);
        return notificationRepository.findById(id).get();
    }
    /**
     * PC 后台根据主键查询
     * @param id
     * @return
     */
    public CcrNotification infoForPC(Integer id) {
        systemLogService.addLog("消息通知", "infoForPC",
                "查询消息通知");

        // application.setHasUsers(hasUsersRepository.findUserIdsByAppId(id));

        return notificationRepository.findById(id).get();
    }

    /**
     * H5获取登录用户消息通知列表
     * @return
     */
    public List<CcrNotification> all(String userCode) {
        systemLogService.addLog("pc应用管理", "info",
                "查询所有pc应用");

        return notificationRepository.findAll();
    }

}
