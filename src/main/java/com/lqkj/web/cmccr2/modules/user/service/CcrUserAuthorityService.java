package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户权限服务
 */
@Service
@Transactional
public class CcrUserAuthorityService {

    @Autowired
    CcrUserAuthorityRepository userAuthorityRepository;

    @Autowired
    CcrUserRuleRepository userRuleRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrUserAuthority add(CcrUserAuthority authority) {
        systemLogService.addLog("用户权限服务", "add",
                "增加一个用户权限");

        authority.setEnabled(Boolean.TRUE);

        return userAuthorityRepository.save(authority);
    }

    public void delete(Long[] id) {
        systemLogService.addLog("用户权限服务", "delete",
                "删除一个用户权限");

        for (Long i : id) {
            userAuthorityRepository.deleteById(i);
        }
    }

    public CcrUserAuthority update(Long id, CcrUserAuthority authority) {
        systemLogService.addLog("用户权限服务", "update",
                "更新一个用户权限");

        CcrUserAuthority savedAuthority = userAuthorityRepository.getOne(id);

        BeanUtils.copyProperties(authority, savedAuthority);

        return userAuthorityRepository.save(savedAuthority);
    }

    public CcrUserAuthority info(Long id) {
        systemLogService.addLog("用户权限服务", "info",
                "查询一个用户权限");

        return userAuthorityRepository.findById(id).get();
    }

    public Page<CcrUserAuthority> page(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("用户权限服务", "page",
                "分页查询用户权限");

        CcrUserAuthority authority = new CcrUserAuthority();
        authority.setName(keyword);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("authorityId");

        return userAuthorityRepository.findAll(Example.of(authority, matcher),
                PageRequest.of(page, pageSize));
    }

    public List<CcrUserAuthority> findByRuleId(Long ruleId) {
        systemLogService.addLog("用户权限服务", "findByRuleId",
                "根据角色查询权限");

        return userRuleRepository.getOne(ruleId).getAuthorities();
    }

    public List<CcrUserAuthority> findByType(CcrUserAuthority.UserAuthorityType type) {
        systemLogService.addLog("用户权限服务", "findByType",
                "根据类型查询权限");

        return userAuthorityRepository.findByType(type);
    }

    public void batchUpdateEnabled(Long[] authorities, Boolean enabled) {
        systemLogService.addLog("用户权限服务", "batchUpdate",
                "批量更新权限状态");

        for (Long authority : authorities) {
            CcrUserAuthority userAuthority = userAuthorityRepository.getOne(authority);

            userAuthority.setEnabled(enabled);

            userAuthorityRepository.save(userAuthority);
        }
    }
}
