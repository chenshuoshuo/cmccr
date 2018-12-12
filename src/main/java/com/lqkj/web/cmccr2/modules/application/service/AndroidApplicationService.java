package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import com.lqkj.web.cmccr2.modules.application.dao.CcrAndroidApplicationRepository;
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

@Service
@Transactional
public class AndroidApplicationService {

    @Autowired
    CcrAndroidApplicationRepository androidApplicationDao;

    @Autowired
    ApplicationCommonService applicationCommonService;

    @Autowired
    CcrSystemLogService systemLogService;

    /**
     * 创建android应用
     */
    public Long createAndroidApplication(CcrAndroidApplication application) {
        systemLogService.addLog("android应用管理","createAndroidApplication",
                "创建android应用");
        return androidApplicationDao.save(application).getId();
    }

    /**
     * 更新android应用
     *
     * @param id 应用id
     */
    public void updateAndroidApplication(Long id, Integer versionCode, String versionName, String updateDescription,
                                         String apkPath) throws Exception {
        CcrAndroidApplication application = androidApplicationDao.getOne(id);

        if (application.getVersionCode() >= versionCode) {
            throw new Exception("版本号不能低于当前版本!");
        }

        application.setVersionCode(versionCode);
        application.setVersionName(versionName);
        application.setUpdateDescription(updateDescription);
        application.setApkPath(apkPath);

        androidApplicationDao.saveAndFlush(application);

        systemLogService.addLog("android应用管理","updateAndroidApplication",
                "更新android应用");
    }

    /**
     * 删除android应用
     *
     * @param id 应用id
     */
    @Transactional
    public void deleteAndroidApplication(Long[] id) throws IOException {
        for (Long i : id) {
            CcrAndroidApplication application = androidApplicationDao.getOne(i);

            Files.delete(Paths.get(application.getApkPath()));

            androidApplicationDao.delete(application);
        }

        systemLogService.addLog("android应用管理","deleteAndroidApplication",
                "批量删除android应用");
    }

    /**
     * 查询应用应用信息
     *
     * @param id 应用id
     * @return 信息
     */
    public CcrAndroidApplication getApplicationById(Long id) {
        systemLogService.addLog("android应用管理","getApplicationById",
                "查询android应用信息");
        return (CcrAndroidApplication) applicationCommonService.setIconURL(androidApplicationDao.findById(id).get());
    }

    /**
     * 查询应用列表
     */
    public Page<CcrAndroidApplication> page(Integer page, Integer pageSize) {
        systemLogService.addLog("android应用管理","page",
                "android应用分页查询");

        Page<CcrAndroidApplication> result = androidApplicationDao.findAll(PageRequest.of(page, pageSize));

        return result.map(v -> (CcrAndroidApplication) applicationCommonService.setIconURL(v));
    }

    /**
     * 输出apk文件到输出流
     *
     * @param id 应用id
     * @param os 输出流
     */
    public void readAndroidStream(Long id, OutputStream os) throws IOException {
        systemLogService.addLog("android应用管理","readAndroidStream",
                "输出apk文件到输出流");

        CcrAndroidApplication application = androidApplicationDao.getOne(id);

        File apkFile = new File(application.getApkPath());

        IOUtils.copy(FileUtils.openInputStream(apkFile), os);
    }

    /**
     * 获取apk文件名称
     *
     * @param id 应用id
     * @return apk文件名称
     */
    public String getApkFileName(Long id) {
        systemLogService.addLog("android应用管理","getApkFileName",
                "获取apk文件名称");

        CcrAndroidApplication application = androidApplicationDao.getOne(id);

        File file = new File(application.getApkPath());

        return file.getName();
    }
}
