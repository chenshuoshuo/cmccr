package com.lqkj.web.cmccr2.modules.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserBatchRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    CcrUserRuleRepository ruleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CcrSystemLogService systemLogService;

    @Autowired
    CmdbeApi cmdbeApi;

    @Autowired
    CcrUserBatchRepository ccrUserBatchRepository;

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
        if(userRepository.findByUserName(ccrUser.getUsername())!=null){
            return null;
        }
        systemLogService.addLog("用户管理服务", "registerAdmin",
                "用户注册");

        ccrUser.setAdmin(Boolean.TRUE);
        ccrUser.setPassWord(passwordEncoder.encode(ccrUser.getPassWord()));
        ccrUser.setUserGroup(CcrUser.CcrUserGroupType.teacher_staff);
        ccrUser.setRules(Sets.newHashSet(ruleRepository.getOne(1L)));
        //先根据用户名进行查询，看是否已经存在该用户

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
    public String update(Long id, String password,String oldPassword ,Boolean admin) {
        systemLogService.addLog("用户管理服务", "update",
                "更新用户密码");

        CcrUser user = userRepository.getOne(id);

        //密码验证
        if (password!=null && passwordEncoder.encode(oldPassword).equals(user.getPassWord())) {
            user.setPassWord(passwordEncoder.encode(password));
        }

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

    /**
     * 退出登录
     * @param userId
     */
    public void loginout(Long userId) {
        CcrUser savedUser = userRepository.getOne(userId);

        savedUser.setCasTicket(null);

        userRepository.save(savedUser);

        SecurityContextHolder.clearContext();
    }

    /**
     * 根据userCode获取用户
     * @return
     */
    public CcrUser findByUserCode(String userCode){
        return userRepository.findByUserName(userCode);
    }

    /**
     * 从CMDBE更新用户
     */
    @Transactional
    public void updateUserFromCmdbe() {
        Boolean hasNext = true;
        int page = 0;
        // 教职工
        while (hasNext){
            StringBuffer userString = new StringBuffer();
            StringBuffer userGroupString = new StringBuffer();
            StringBuffer userRuleString = new StringBuffer();
            String password = passwordEncoder.encode("123456");

            ObjectNode result = cmdbeApi.pageQueryTeachingStaff(null, null, page, 2000);
            hasNext = !(result.get("last").booleanValue());
            page += 1;

            Iterator<JsonNode> iterator = result.get("content").iterator();
            while (iterator.hasNext()){
                JsonNode jsonNode = iterator.next();
                userString.append(jsonNode.get("staffNumber").textValue() + ",");
                userGroupString.append("teacher_staff,");
                userRuleString.append("2,");
            }
            if(StringUtils.isNotBlank(userString.toString())){
                executeSql(userString, userGroupString,userRuleString,password);
            }

            //
        }

        hasNext = true;
        page = 0;
        // 学生
        while (hasNext){
            StringBuffer userString = new StringBuffer();
            StringBuffer userGroupString = new StringBuffer();
            StringBuffer userRuleString = new StringBuffer();
            String password = passwordEncoder.encode("123456");
            ObjectNode result = cmdbeApi.pageQueryStudentInfo(null, null, page, 2000);
            hasNext = !(result.get("last").booleanValue());
            page += 1;

            Iterator<JsonNode> iterator = result.get("content").iterator();
            while (iterator.hasNext()){
                JsonNode jsonNode = iterator.next();
                userString.append(jsonNode.get("studentNo").textValue() + ",");
                userGroupString.append("student,");
                userRuleString.append("3,");
            }
            if(StringUtils.isNotBlank(userString.toString())){
                executeSql(userString, userGroupString,userRuleString,password);
            }
        }

    }

    private void executeSql(StringBuffer userCodeString, StringBuffer userGroupString,StringBuffer userRuleString,String password){
        if(userCodeString.length() > 0){
            userCodeString = userCodeString.deleteCharAt(userCodeString.length() - 1);
            userGroupString = userGroupString.deleteCharAt(userGroupString.length() - 1);
            userRuleString =  userRuleString.deleteCharAt(userRuleString.length() - 1);
        }
        StringBuffer sqlString = new StringBuffer();
        sqlString.append("select fun_ccr_update_usr('")
                .append(userCodeString)
                .append("','")
                .append(userGroupString)
                .append("','")
                .append(userRuleString)
                .append("','")
                .append(password)
                .append("');");
        logger.info(sqlString.toString());
        ccrUserBatchRepository.bulkMergeUser(sqlString.toString());
    }


}
