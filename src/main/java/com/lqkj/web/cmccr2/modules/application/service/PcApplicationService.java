package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrApplicationHasUsersRepository;
import com.lqkj.web.cmccr2.modules.application.dao.CcrPcApplicationBatchDao;
import com.lqkj.web.cmccr2.modules.application.dao.CcrPcApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsers;
import com.lqkj.web.cmccr2.modules.application.domain.CcrPcApplication;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * pc应用服务
 */
@Service
@Transactional
public class PcApplicationService {

    @Autowired
    CcrPcApplicationRepository applicationRepository;

    @Autowired
    CcrApplicationHasUsersRepository hasUsersRepository;

    @Autowired
    CcrPcApplicationBatchDao pcApplicationBatchDao;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrPcApplication add(CcrPcApplication application) {
        systemLogService.addLog("pc应用管理", "add",
                "增加pc应用");

        CcrPcApplication pcApplication = applicationRepository.save(application);

        if (application.getHasUsers()!=null) {
            List<CcrApplicationHasUsers> hasUsers = application.getHasUsers().stream()
                    .map(v -> new CcrApplicationHasUsers(pcApplication.getAppId(), v))
                    .collect(Collectors.toList());

            pcApplicationBatchDao.batchInsertHasUsers(hasUsers);
        }
        return pcApplication;
    }

    public void delete(Long appId) {
        systemLogService.addLog("pc应用管理", "delete",
                "删除pc应用");

        hasUsersRepository.deleteByAppId(appId);

        applicationRepository.deleteById(appId);
    }

    public CcrPcApplication update(Long id, CcrPcApplication application) {
        systemLogService.addLog("pc应用管理", "update",
                "更新pc应用");

        CcrPcApplication savedApp = applicationRepository.getOne(id);

        BeanUtils.copyProperties(application, savedApp);

        hasUsersRepository.deleteByAppId(id);

        if (application.getHasUsers()!=null) {
            List<CcrApplicationHasUsers> hasUsers = application.getHasUsers().stream()
                    .map(v -> new CcrApplicationHasUsers(id, v))
                    .collect(Collectors.toList());

            pcApplicationBatchDao.batchInsertHasUsers(hasUsers);
        }

        return applicationRepository.save(savedApp);
    }

    public CcrPcApplication info(Long id) {
        systemLogService.addLog("pc应用管理", "info",
                "查询pc应用");

        CcrPcApplication application = applicationRepository.findById(id).get();

        application.setHasUsers(hasUsersRepository.findUserIdsByAppId(id));

        return application;
    }

    public List<CcrPcApplication> all() {
        systemLogService.addLog("pc应用管理", "info",
                "查询所有pc应用");

        return applicationRepository.findAll();
    }

    public List<CcrPcApplication> findByParentId(Long parentMenuId) {
        systemLogService.addLog("pc应用管理", "findByParentId",
                "根据菜单id查询应用");

        return applicationRepository.findByMenuId(parentMenuId);
    }
}
