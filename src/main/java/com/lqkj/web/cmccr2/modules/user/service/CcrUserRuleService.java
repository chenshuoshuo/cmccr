package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * 用户角色服务
 */
@Service
@Transactional
public class CcrUserRuleService {

    @Autowired
    CcrUserRuleRepository userRuleRepository;

    @Autowired
    CcrUserAuthorityRepository userAuthorityRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrUserRule add(String name, String enName, Long[] authorities) {
        systemLogService.addLog("用户角色服务", "add",
                "增加用户角色");

        CcrUserRule rule = new CcrUserRule();

        rule.setName(name);
        rule.setContent(enName);
        rule.setAuthorities(new ArrayList<>());

        for (Long authority : authorities) {
            rule.getAuthorities().add(userAuthorityRepository.getOne(authority));
        }

        return userRuleRepository.save(rule);
    }

    public void delete(Long[] id) {
        systemLogService.addLog("用户角色服务", "delete",
                "删除用户角色");

        for (Long i : id) {
            userRuleRepository.deleteById(i);
        }
    }

    public CcrUserRule update(Long id, String name, String enName, Long[] authorities) {
        systemLogService.addLog("用户角色服务", "update",
                "更新用户角色");

        CcrUserRule rule = userRuleRepository.getOne(id);

        rule.setName(name);
        rule.setContent(enName);

        if (rule.getAuthorities()==null) {
            rule.setAuthorities(new ArrayList<>());
        } else {
            rule.getAuthorities().clear();
        }

        for (Long authority : authorities) {
            rule.getAuthorities().add(userAuthorityRepository.getOne(authority));
        }

        return userRuleRepository.save(rule);
    }

    public CcrUserRule info(Long id) {
        systemLogService.addLog("用户角色服务", "add",
                "查询用户角色");

        return userRuleRepository.findById(id).get();
    }

    public Page<CcrUserRule> page(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("用户角色服务", "add",
                "分页查询用户角色");

        CcrUserRule rule = new CcrUserRule();
        rule.setName(keyword);
        rule.setContent(keyword);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("ruleId");

        return userRuleRepository.findAll(Example.of(rule, exampleMatcher),
                PageRequest.of(page, pageSize));
    }
}
