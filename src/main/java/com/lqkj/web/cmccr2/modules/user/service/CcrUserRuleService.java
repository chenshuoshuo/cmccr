package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrRuleAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

/**
 * 用户角色服务
 */
@Service
@Transactional
public class CcrUserRuleService {

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    CcrUserRuleRepository userRuleRepository;

    @Autowired
    CcrUserAuthorityRepository userAuthorityRepository;

    @Resource
    CcrRuleAuthorityRepository ruleAuthorityRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    public CcrUserRule add(String name, String enName, Long[] authorities) {
        systemLogService.addLog("用户角色服务", "add",
                "增加用户角色");

        //判断用户角色名是否存在
        Boolean exits=userRuleRepository.findByRuleName(name,enName).isEmpty();
        if(!exits){
            return null;
        }
        CcrUserRule rule = new CcrUserRule();

        rule.setName(name);
        rule.setContent(enName);
        rule.setAuthorities(new HashSet<>());

        for (Long authority : authorities) {
            rule.getAuthorities().add(userAuthorityRepository.getOne(authority));
        }

        rule = userRuleRepository.save(rule);

        appendUserToNowUser(rule);

        return rule;
    }

    /**
     * 增加角色到当前用户
     */
    private void appendUserToNowUser(CcrUserRule rule) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CcrUser ccrUser = userRepository.findByUserName(username);

        ccrUser.getRules().add(rule);

        userRepository.save(ccrUser);
    }

    @Transactional
    public void delete(Long[] id) {
        systemLogService.addLog("用户角色服务", "delete",
                "删除用户角色");

        for (Long i : id) {
            //先删除权限与角色的关联
            ruleAuthorityRepository.deleteByRuleId(i);
            userRuleRepository.deleteById(i);
        }
    }

    public CcrUserRule update(Long id, String name, String enName, Long[] authorities) {
        systemLogService.addLog("用户角色服务", "update",
                "更新用户角色");

        CcrUserRule rule = userRuleRepository.getOne(id);

        rule.setUpdateTime(new Timestamp(new Date().getTime()));

        if (rule.getAuthorities()==null) {
            rule.setAuthorities(new HashSet<>());
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

        String k = keyword==null ? "" : keyword;

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRuleRepository.findSupportRules(username, "%" + k + "%",
                PageRequest.of(page, pageSize));
    }
}
