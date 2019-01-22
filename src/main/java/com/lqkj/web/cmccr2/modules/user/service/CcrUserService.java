package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements UserDetailsService {

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    CcrUserRuleRepository ruleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CcrSystemLogService systemLogService;

    @Value("${admin.code}")
    Integer adminCode;

    /**
     * 密码登录
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        systemLogService.addLog("用户管理服务", "loadClientByClientId",
                "普通用户查询");

        return userRepository.findByUserName(username);
    }

    /**
     * 注册管理员用户
     */
    public CcrUser registerAdmin(Integer adminCode, CcrUser ccrUser) throws Exception {
        if (!adminCode.equals(this.adminCode)) {
            throw new Exception("授权码不正确");
        }
        systemLogService.addLog("用户管理服务", "registerAdmin",
                "用户注册");

        ccrUser.setAdmin(Boolean.TRUE);
        ccrUser.setPassWord(passwordEncoder.encode(ccrUser.getPassword()));

        return userRepository.save(ccrUser);
    }

    /**
     * 查询用户信息
     */
    public CcrUser info(Long id) {
        systemLogService.addLog("用户管理服务", "info",
                "用户信息查询");

        return userRepository.findById(id).get();
    }

    /**
     * 更新用户密码
     */
    public String update(Long id, String password, Boolean admin) {
        systemLogService.addLog("用户管理服务", "update",
                "更新用户密码");

        CcrUser user = userRepository.getOne(id);

        if (password!=null) user.setPassWord(passwordEncoder.encode(password));
        if (admin!=null) user.setAdmin(admin);

        userRepository.save(user);

        return password;
    }

    /**
     * 删除用户
     */
    public void delete(Long id) {
        systemLogService.addLog("用户管理服务", "delete",
                "删除用户");

        userRepository.deleteById(id);
    }

    /**
     * 分页搜索
     */
    public Page<CcrUser> page(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("用户管理服务", "page",
                "分页查询用户列表");

        CcrUser ccrUser = new CcrUser();
        ccrUser.setUserCode(keyword);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("userCode", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("userId");

        return userRepository.findAll(Example.of(ccrUser, exampleMatcher),
                PageRequest.of(page, pageSize));
    }

    /**
     * 查询用户角色
     */
    public Set<CcrUserRule> findRulesByUserId(Long id) {
        systemLogService.addLog("用户管理服务", "findRulesByUserId",
                "查询用户角色");

        return userRepository.getOne(id).getRules();
    }

    /**
     * 用戶統計
     */
    public List<Object[]> userGroup() {
        systemLogService.addLog("用户管理服务", "userGroup",
                "用戶統計");

        return userRepository.userStatistics();
    }

    /**
     * 绑定角色
     */
    public void bindRules(Long userId, Long[] rules) {
        systemLogService.addLog("用户管理服务", "bindRules",
                "绑定用户角色");

        CcrUser user = userRepository.getOne(userId);

        user.getRules().clear();

        for (Long rule : rules) {
            user.getRules().add(this.ruleRepository.getOne(rule));
        }

        userRepository.save(user);
    }
}
