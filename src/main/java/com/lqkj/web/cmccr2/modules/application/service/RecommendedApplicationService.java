package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrAndroidApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.dao.CcrRecommendedApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import com.lqkj.web.cmccr2.modules.application.domain.CcrRecommendedApplication;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
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
    public List<CcrRecommendedApplication> createBatchRecommendedApplication(List<CcrRecommendedApplication> applicationList) {
        systemLogService.addLog("推荐应用管理", "createBulkRecommendedApplication",
                "批量创建推荐应用");

        return recommendedApplicationDao.saveAll(applicationList);
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
