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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements UserDetailsService {
    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        systemLogService.addLog("用户管理服务", "registerAdmin",
                "用户注册");

        ccrUser.setAdmin(Boolean.TRUE);
        ccrUser.setPassWord(passwordEncoder.encode(ccrUser.getPassword()));
        ccrUser.setUserGroup(CcrUser.CcrUserGroupType.teacher_staff);
        ccrUser.setRules(Sets.newHashSet(ruleRepository.getOne(1L)));

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
            StringBuffer stringBuffer = new StringBuffer();

            ObjectNode result = cmdbeApi.pageQueryTeachingStaff(null, null, page, 1000);
            hasNext = !(result.get("last").booleanValue());
            page += 1;

            Iterator<JsonNode> iterator = result.get("content").iterator();
            while (iterator.hasNext()){
                JsonNode jsonNode = iterator.next();
                //System.out.println(jsonNode);
                String userCode = jsonNode.get("staffNumber").textValue();
                stringBuffer.append(loadSql(userCode, "teacher_staff"));
            }
            ccrUserBatchRepository.bulkMergeUser(stringBuffer.toString());
        }

        hasNext = true;
        page = 0;
        // 学生
        while (hasNext){
            StringBuffer stringBuffer = new StringBuffer();

            ObjectNode result = cmdbeApi.pageQueryStudentInfo(null, null, page, 1000);
            hasNext = !(result.get("last").booleanValue());
            page += 1;

            Iterator<JsonNode> iterator = result.get("content").iterator();
            while (iterator.hasNext()){
                JsonNode jsonNode = iterator.next();
                //System.out.println(jsonNode);
                String userCode = jsonNode.get("studentNo").textValue();
                stringBuffer.append(loadSql(userCode, "student"));
            }
            ccrUserBatchRepository.bulkMergeUser(stringBuffer.toString());
        }

    }

    private String loadSql(String userCode, String userGroup){
        StringBuffer stringBuffer = new StringBuffer();
        //logger.info(userCode);
        CcrUser ccrUser = userRepository.findByUserName(userCode);
        if(ccrUser == null){
            stringBuffer.append("insert into ccr_user values(")
                    .append("nextval('ccr_user_user_id_seq'::regclass),") // userId
                    .append("null,") // openid
                    .append("null,") // passWord
                    .append("'" + userCode + "',") // userCode
                    .append("null,") // casTicket
                    .append("teacher_staff,") // userGroup
                    .append("now(),") // updateTime
                    .append("'f');"); // isAdmin
        } else if(!userGroup.equals(ccrUser.getUserGroup().toString())){
            stringBuffer.append("update ccr_user set user_group = '" + userGroup + "' where user_code = '" + userCode + "';");
        }
        return stringBuffer.length() > 0 ? stringBuffer.toString() : "";
    }
}
