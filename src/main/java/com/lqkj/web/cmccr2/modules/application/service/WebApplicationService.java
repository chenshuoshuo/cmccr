package com.lqkj.web.cmccr2.modules.application.service;

import com.lqkj.web.cmccr2.modules.application.dao.CcrWebApplicationRepository;
import com.lqkj.web.cmccr2.modules.application.domain.CcrWebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class WebApplicationService {

    @Autowired
    public CcrWebApplicationRepository webApplicationDao;

    @Autowired
    public ApplicationCommonService applicationCommonService;

    /**
     * 创建web应用
     */
    public Long createApplication(CcrWebApplication webApplication) throws Exception {
        if (webApplication.getGatewayForwardId() != null | !StringUtils.isEmpty(webApplication.getUrl())) {
            return webApplicationDao.save(webApplication).getId();
        } else {
            throw new Exception("请数据访问地址或者api网关id");
        }
    }

    /**
     * 删除应用
     *
     * @param id 应用id
     */
    @Transactional
    public void deleteApplication(Long[] id) {
        for (Long i : id) {
            webApplicationDao.deleteById(i);
        }
    }

    /**
     * 更新web應用地址
     */
    public Long updateApplication(CcrWebApplication application) {
        return webApplicationDao.saveAndFlush(application).getId();
    }

    /**
     * 查詢应用信息
     */
    public CcrWebApplication getApplicationById(Long id) {
        CcrWebApplication application = webApplicationDao.findById(id).get();
        return (CcrWebApplication) applicationCommonService.setIconURL(application);
    }

    /**
     * 分頁查詢
     */
    public Page<CcrWebApplication> getWebApplicationPage(Integer page, Integer pageSize) {
        return webApplicationDao.findAll(PageRequest.of(page, pageSize))
                .map(v -> (CcrWebApplication) applicationCommonService.setIconURL(v));
    }
}
