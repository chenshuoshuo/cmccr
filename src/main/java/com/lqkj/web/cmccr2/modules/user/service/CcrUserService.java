package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken>, UserDetailsService {

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CcrSystemLogService systemLogService;

    @Value("${admin.code}")
    Integer adminCode;

    /**
     * cas登录
     */
    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken casAssertionAuthenticationToken) throws UsernameNotFoundException {
        systemLogService.addLog("用户管理服务", "loadUserDetails",
                "cas用户查询");

        return userRepository.findByUserName(casAssertionAuthenticationToken.getName());
    }

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
    public String updatePassword(Long id, String password) {
        systemLogService.addLog("用户管理服务", "updatePassword",
                "更新用户密码");

        CcrUser user = userRepository.getOne(id);

        user.setPassWord(passwordEncoder.encode(password));

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

    public List<CcrUserRule> findRulesByUserId(Long id) {
        systemLogService.addLog("用户管理服务", "findRulesByUserId",
                "查询用户角色");

        return userRepository.getOne(id).getRules();
    }
}
