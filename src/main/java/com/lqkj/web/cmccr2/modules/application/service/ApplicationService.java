package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplication;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * pc应用服务
 */
@Service
@Transactional
public class ApplicationService {

    @Autowired
    CcrApplicationRepository applicationRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrApplication add(CcrApplication application) {
        systemLogService.addLog("pc应用管理","add",
                "增加pc应用");

        return applicationRepository.save(application);
    }

    public void delete(Long appId) {
        systemLogService.addLog("pc应用管理","delete",
                "删除pc应用");

        applicationRepository.deleteById(appId);
    }

    public CcrApplication update(Long id, CcrApplication application) {
        systemLogService.addLog("pc应用管理","update",
                "更新pc应用");

        CcrApplication savedApp = applicationRepository.getOne(id);

        BeanUtils.copyProperties(application, savedApp);

        return applicationRepository.save(savedApp);
    }

    public CcrApplication info(Long id) {
        systemLogService.addLog("pc应用管理","info",
                "查询pc应用");

        return applicationRepository.findById(id).get();
    }

    public List<CcrApplication> all() {
        systemLogService.addLog("pc应用管理","info",
                "查询所有pc应用");

        return applicationRepository.findAll();
    }
}
