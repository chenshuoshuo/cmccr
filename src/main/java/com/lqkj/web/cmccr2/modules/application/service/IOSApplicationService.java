package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.domain.CcrIosApplication;
import com.lqkj.web.cmccr2.modules.application.dao.CcrIosApplicationRepository;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IOSApplicationService {
    @Autowired
    CcrIosApplicationRepository iosApplicationDao;

    @Autowired
    ApplicationCommonService applicationCommonService;

    @Autowired
    CcrSystemLogService systemLogService;

    /**
     * 创建ios应用
     */
    public Long createApplication(CcrIosApplication application) throws Exception {
        systemLogService.addLog("ios应用管理","createApplication",
                "创建ios应用");

        return iosApplicationDao.save(application).getId();
    }

    /**
     * 删除ios应用
     *
     * @param id 应用id
     */
    @Transactional
    public void deleteApplication(Long[] id) {
        systemLogService.addLog("ios应用管理","deleteApplication",
                "删除ios应用");

        for (Long i : id) {
            iosApplicationDao.deleteById(i);
        }
    }

    /**
     * 更新ios应用
     */
    public Long updateApplication(CcrIosApplication application) throws Exception {
        systemLogService.addLog("ios应用管理","updateApplication",
                "更新ios应用");

        return iosApplicationDao.saveAndFlush(application).getId();
    }

    /**
     * 根据id获取应用信息
     */
    public CcrIosApplication getIOSApplicationById(Long id) {
        systemLogService.addLog("ios应用管理","getIOSApplicationById",
                "查询ios应用信息");

        CcrIosApplication application = iosApplicationDao.findById(id).get();

        return (CcrIosApplication) applicationCommonService.setIconURL(application);
    }

    /**
     * 分页查询
     *
     * @param page     当前页数
     * @param pageSize 个数
     */
    public Page<CcrIosApplication> page(Integer page, Integer pageSize) {
        systemLogService.addLog("ios应用管理","page",
                "分页查询ios应用");

        return iosApplicationDao.findAll(PageRequest.of(page, pageSize))
                .map(v -> (CcrIosApplication) applicationCommonService.setIconURL(v));
    }
}
