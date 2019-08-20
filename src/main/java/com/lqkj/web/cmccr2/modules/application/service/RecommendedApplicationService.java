package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrAndroidApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.dao.CcrRecommendedApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import com.lqkj.web.cmccr2.modules.application.domain.CcrRecommendedApplication;
import com.lqkj.web.cmccr2.modules.application.domain.RecommendedApplicationVO;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.utils.UUIDUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecommendedApplicationService {

    @Autowired
    CcrRecommendedApplicationRepository recommendedApplicationDao;

    @Autowired
    CcrSystemLogService systemLogService;

    /**
     * 创建推荐应用
     */
    public CcrRecommendedApplication createRecommendedApplication(CcrRecommendedApplication application) {
        systemLogService.addLog("推荐应用管理", "createRecommendedApplication",
                "创建推荐应用");
        return recommendedApplicationDao.save(application);
    }
    /**
     * 批量创建推荐应用
     */
    public List<CcrRecommendedApplication> createBatchRecommendedApplication(List<RecommendedApplicationVO> applicationList) throws Exception {
        systemLogService.addLog("推荐应用管理", "createBulkRecommendedApplication",
                "批量创建推荐应用");
        List<CcrRecommendedApplication> applications = new ArrayList<>();
        List<CcrRecommendedApplication> list = recommendedApplicationDao.findAll();
        for(RecommendedApplicationVO applicationVO:applicationList){
            CcrRecommendedApplication application = new CcrRecommendedApplication();
            Timestamp start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(applicationVO.getStartTime()).getTime());
            Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH").parse(applicationVO.getEndTime()).getTime());
            String appId = UUIDUtils.getUUID();
            if(applicationVO.getAppId() == null || applicationVO.getAppId().equals("")){
                application.setAppId(appId);
            }else {
                application.setAppId(applicationVO.getAppId());
            }
            application.setAppUrl(applicationVO.getAppUrl());
            application.setStartTime(start);
            application.setEndTime(end);
            application.setAppLogo(applicationVO.getAppLogo());
            application.setAppName(applicationVO.getAppName());
            application.setMemo(applicationVO.getMemo());
            if(list.size() > 0){
                if(applicationVO.getOrderId() == null){
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+1);
                        recommendedApplicationDao.save(list.get(i));
                    }
                    application.setOrderId(list.size()+1);
                }
                if(1 == applicationVO.getOrderId()){
                    application.setOrderId(applicationVO.getOrderId());
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+2);
                        recommendedApplicationDao.save(list.get(i));
                    }
                }
                if(applicationVO.getOrderId() > 1 && applicationVO.getOrderId() <= list.size()){
                    application.setOrderId(applicationVO.getOrderId());
                    for(int i = 0; i < application.getOrderId()-1;i++){
                        list.get(i).setOrderId(i+1);
                        recommendedApplicationDao.save(list.get(i));
                    }
                    for(int i = applicationVO.getOrderId()-1; i < list.size();i++){
                        list.get(i).setOrderId(i+2);
                        recommendedApplicationDao.save(list.get(i));
                    }
                }
                if(applicationVO.getOrderId() > list.size()){
                    application.setOrderId(applicationVO.getOrderId());
                    for(int i = 0; i < list.size();i++){
                        list.get(i).setOrderId(i+1);
                        recommendedApplicationDao.save(list.get(i));
                    }
                    application.setOrderId(list.size()+1);
                }
            }else{
                application.setOrderId(1);
            }
            applications.add(application);
        }
        return recommendedApplicationDao.saveAll(applications);
    }

    /**
     * 更新推荐应用
     *
     * @param id 推荐应用ID
     */
    public CcrRecommendedApplication updateRecommendedApplication(String id, String appName, String appUrl,String appLogo, Timestamp startTime,Timestamp endTime,Integer orderId,String memo) throws Exception {
        CcrRecommendedApplication application = recommendedApplicationDao.getOne(id);
        if(appLogo != null){ application.setAppLogo(appLogo);}
        application.setAppName(appName);
        application.setAppUrl(appUrl);
        application.setStartTime(startTime);
        application.setEndTime(endTime);
        application.setOrderId(orderId);
        if(memo != null){application.setMemo(memo);}
        systemLogService.addLog("推荐应用管理", "updateRecommendedApplication",
                "更新推荐应用");
        return recommendedApplicationDao.save(application);

    }

    /**
     * 删除推荐应用
     *
     * @param id 应用id
     */
    @Transactional
    public void deleteRecommendedApplication(String[] id) throws IOException {
        for (String i : id) {
            CcrRecommendedApplication application = recommendedApplicationDao.getOne(i);

            recommendedApplicationDao.delete(application);
        }

        systemLogService.addLog("推荐应用管理", "deleteRecommendedApplication",
                "批量删除推荐应用");
    }

    /**
     * 查询推荐应用信息
     *
     * @param id 应用id
     * @return 信息
     */
    public CcrRecommendedApplication getRecommendedApplicationById(String id) {
        systemLogService.addLog("推荐应用管理", "CcrRecommendedApplication",
                "查询推荐应用信息");
        return recommendedApplicationDao.findById(id).get();
    }

    /**
     * 根据时间查询应用列表
     */
    public List<CcrRecommendedApplication> queryList(Timestamp systemTime) {
        systemLogService.addLog("推荐应用管理", "queryList",
                "推荐应用管理列表查询");

       return recommendedApplicationDao.findAllByTime(systemTime);
    }
    /**
     * 查询所有应用列表
     */
    public List<CcrRecommendedApplication> queryAllList() {
        systemLogService.addLog("推荐应用管理", "queryList",
                "推荐应用管理列表查询");

        return recommendedApplicationDao.findAll();
    }


}
