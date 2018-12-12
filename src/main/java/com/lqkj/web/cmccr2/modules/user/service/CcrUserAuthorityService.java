package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户权限服务
 */
@Service
public class CcrUserAuthorityService {

    @Autowired
    CcrUserAuthorityRepository userAuthorityRepository;

    @Autowired
    CcrUserRuleRepository userRuleRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrUserAuthority add(CcrUserAuthority authority) {
        systemLogService.addLog("用户权限服务","add",
                "增加一个用户权限");

        return userAuthorityRepository.save(authority);
    }

    public void delete(Long id) {
        systemLogService.addLog("用户权限服务","delete",
                "删除一个用户权限");

        userAuthorityRepository.deleteById(id);
    }

    public CcrUserAuthority update(Long id, CcrUserAuthority authority) {
        systemLogService.addLog("用户权限服务","update",
                "更新一个用户权限");

        CcrUserAuthority savedAuthority = userAuthorityRepository.getOne(id);

        savedAuthority.setContent(authority.getContent());
        savedAuthority.setName(authority.getName());
        savedAuthority.setRoute(authority.getRoute());
        savedAuthority.setParentId(authority.getParentId());

        return userAuthorityRepository.save(savedAuthority);
    }

    public CcrUserAuthority info(Long id) {
        systemLogService.addLog("用户权限服务","info",
                "查询一个用户权限");

        return userAuthorityRepository.findById(id).get();
    }

    public List<CcrUserAuthority> findByRuleId(Long ruleId) {
        systemLogService.addLog("用户权限服务","findByRuleId",
                "根据角色查询权限");

        return userRuleRepository.getOne(ruleId).getAuthorities();
    }

    public List<CcrUserAuthority> findByType(CcrUserAuthority.UserAuthorityType type) {
        systemLogService.addLog("用户权限服务","findByType",
                "根据类型查询权限");

        return userAuthorityRepository.findByType(type);
    }
}
