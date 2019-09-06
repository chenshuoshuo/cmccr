package com.lqkj.web.cmccr2.modules.notification.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsers;
import com.lqkj.web.cmccr2.modules.application.domain.CcrPcApplication;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrNotificationRepository;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrNotificationSQLDao;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotificationRead;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotificationVO;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import com.lqkj.web.cmccr2.utils.ExcelUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.UnaryOperator;
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
    NotificationReadService readService;
    @Autowired
    CcrUserService userService;
    @Autowired
    CcrNotificationSQLDao notificationSQLDao;

    @Autowired
    CcrSystemLogService systemLogService;


    public CcrNotification add(CcrNotification notification) {
        systemLogService.addLog("消息通知", "add",
                "增加消息通知配置");
        Timestamp postTime = new Timestamp(new Date().getTime());
        notification.setPostTime(postTime);
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
        readService.add(notificationRead);
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
    public List<Map<String,Object>> listForH5(String userId, String roles) {
        systemLogService.addLog("消息通知", "listForH5",
                "查询消息通知列表");

        String sql = "select cn.info_id,cn.title,cn.content,cnr.user_code is not null check_read from (select * from ccr_notification where " +
        "target_user_role && ARRAY["+roles+",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['"+userId+"'] \\:\\:varchar[] order by post_time) cn \n" +
                "left join ccr_notification_read cnr on cn.info_id = cnr.info_id and cnr.user_code = " + userId;

        return notificationSQLDao.executeMapSql(sql);
    }

    /**
     * 分页查询
     */
    public Page<CcrNotification> page(String title,String auth,Integer page,Integer pageSize) {
        systemLogService.addLog("消息通知", "page",
                "分页查询消息通知列表");
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<CcrNotification> pageList = notificationRepository.page(title,auth,pageable);
        List<CcrNotification> list = pageList.getContent();
        if(list.size()>0){
            for(CcrNotification notification:list){
                String[] userRole = notification.getTargetUserRole();
                StringBuffer setAuth = new StringBuffer();
                if(userRole.length == 1){
                    if(userRole[0].equals("public")){
                        notification.setAuth(setAuth.append("游客").toString());
                    }
                    if(userRole[0].equals("teacher")){
                        notification.setAuth(setAuth.append("教职工").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                    if(userRole[0].equals("student")){
                        notification.setAuth(setAuth.append("学生").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                }else if(userRole.length > 1){
                    if(Arrays.asList(userRole).contains("teacher") && Arrays.asList(userRole).contains("student")){
                        notification.setAuth(setAuth.append("教职工、学生").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                }else {
                    notification.setAuth(setAuth.append("指定用户").toString());
                }
            }
        }
        return pageList;
    }

    public void export(String title,String auth, OutputStream os) throws IOException {
        List<CcrNotification> notificationList = notificationRepository.list(title,auth);
        List<CcrNotification> notifications = setAuth(notificationList);
        SXSSFWorkbook workbook = new SXSSFWorkbook(10);

        Sheet sheet = workbook.createSheet();

        //设置头
        Row rootRow = sheet.createRow(0);
        rootRow.createCell(0).setCellValue("标题");
        rootRow.createCell(1).setCellValue("内容");
        rootRow.createCell(2).setCellValue("权限");
        rootRow.createCell(3).setCellValue("发布者");
        rootRow.createCell(4).setCellValue("更新时间");

        for (int i = 0; i < notifications.size(); i++) {
            CcrNotification notification = notifications.get(i);

            Row dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(notification.getTitle());
            dataRow.createCell(1).setCellValue(notification.getContent());
            dataRow.createCell(2).setCellValue(notification.getAuth());
            dataRow.createCell(3).setCellValue(notification.getAuthorId());
            dataRow.createCell(4).setCellValue(notification.getPostTime());
        }

        workbook.write(os);

        workbook.dispose();
    }

    /**
     * 导出
     * @throws IOException
     */
    public ResponseEntity<InputStreamResource> download(String title,String auth) throws IOException {
        List<CcrNotification> notificationList = notificationRepository.list(title,auth);
        List<CcrNotification> notifications = setAuth(notificationList);
        List<CcrNotification> list = JSON.parseArray(JSON.toJSONString(notifications), CcrNotification.class);
        return ExcelUtils.downloadOneSheetExcel(CcrNotification.class, list, "notification", "notification.xlsx");
    }

    public List<CcrNotification> setAuth(List<CcrNotification> notificationList){
        if(notificationList.size()>0){
            for(CcrNotification notification:notificationList){
                String[] userRole = notification.getTargetUserRole();
                StringBuffer setAuth = new StringBuffer();
                if(userRole.length == 1){
                    if(userRole[0].equals("public")){
                        notification.setAuth(setAuth.append("游客").toString());
                    }
                    if(userRole[0].equals("teacher")){
                        notification.setAuth(setAuth.append("教职工").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                    if(userRole[0].equals("student")){
                        notification.setAuth(setAuth.append("学生").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                }else if(userRole.length > 1){
                    if(Arrays.asList(userRole).contains("teacher") && Arrays.asList(userRole).contains("student")){
                        notification.setAuth(setAuth.append("教职工、学生").toString());
                        if(notification.getSpecifyUserId() != null){
                            notification.setAuth(setAuth.append("、指定用户").toString());
                        }
                    }
                }else {
                    notification.setAuth(setAuth.append("指定用户").toString());
                }
            }
        }
        return notificationList;
    }

}
