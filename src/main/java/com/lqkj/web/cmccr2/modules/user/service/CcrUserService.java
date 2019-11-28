package com.lqkj.web.cmccr2.modules.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserBatchRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String UPLOAD_FILE_PATH = "./upload/user/";

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

    public CcrUser findByUserName(String userName){
        systemLogService.addLog("用户管理服务", "findByUserName",
                "普通用户查询");
        return userRepository.findByUserName(userName);
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

        return this.setIconURL(userRepository.findById(id).get());
    }

    /**
     * 更新用户密码和头像
     */
    public CcrUser update(Long id, String password,String oldPassword ,Boolean admin,String headPath,String userName) {
        systemLogService.addLog("用户管理服务", "update",
                "更新用户密码和头像");

        CcrUser user = userRepository.findById(id).get();
        //密码验证
        Boolean isUpdate = false;
        if (password!=null && passwordEncoder.matches(oldPassword,user.getPassWord())) {
            user.setPassWord(passwordEncoder.encode(password));
            isUpdate = true;
        }
        if (admin!=null){
            user.setAdmin(admin);
            isUpdate = true;
        }
        if(StringUtils.isNotBlank(headPath)){
            user.setHeadPath(headPath);
            isUpdate = true;
        }
        if(StringUtils.isNotBlank(userName)){
            user.setHeadPath(userName);
            isUpdate = true;
        }
        if(isUpdate){
            userRepository.save(user);
        }
        return this.setIconURL(user);
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
        Page<CcrUser> result = userRepository.findAll(Example.of(ccrUser, exampleMatcher),
                PageRequest.of(page, pageSize));
        return result.map(v -> (CcrUser)this.setIconURL(v));
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
        return this.setIconURL(userRepository.findByUserName(userCode));
    }

    /**
     * 从CMDBE更新用户
     */
    public String updateUserFromCmdbe() {
        StringBuilder errerUserCode = new StringBuilder();
        Boolean hasNext = true;
        int page = 0;
        // 教职工
        while (hasNext){
            //StringBuffer userString = new StringBuffer();
            //StringBuffer userGroupString = new StringBuffer();
            //StringBuffer userRuleString = new StringBuffer();
            String password = passwordEncoder.encode("123456");

            ObjectNode result = cmdbeApi.pageQueryTeachingStaff(null, null, page, 2000);
            hasNext = !(result.get("last").booleanValue());
            page += 1;

            Iterator<JsonNode> iterator = result.get("content").iterator();
            while (iterator.hasNext()){
                JsonNode jsonNode = iterator.next();
                try {
                    String userCode = jsonNode.get("staffNumber").textValue();
                    CcrUser ccrUser = userRepository.findByUserName(userCode);
                    if(ccrUser==null){
                        ccrUser = new CcrUser();
                        ccrUser.setUserCode(userCode);
                        ccrUser.setAdmin(false);
                        ccrUser.setUserGroup(CcrUser.CcrUserGroupType.teacher_staff);
                        ccrUser.setPassWord(password);
                        ccrUser.setUserName(jsonNode.get("realName").textValue());
                        ccrUser.setRules(Sets.newHashSet(ruleRepository.getOne(2L)));
                        userRepository.save(ccrUser);
                    }
                }catch (Exception e){
                    errerUserCode.append(jsonNode.get("staffNumber").textValue()+",");
                    logger.error(e.getMessage(),e);
                    continue;
                }
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
                try {
                    String userCode = jsonNode.get("studentNo").textValue();
                    CcrUser ccrUser = userRepository.findByUserName(userCode);
                    if(ccrUser==null){
                        ccrUser = new CcrUser();
                        ccrUser.setUserCode(userCode);
                        ccrUser.setAdmin(false);
                        ccrUser.setUserGroup(CcrUser.CcrUserGroupType.student);
                        ccrUser.setUserName(jsonNode.get("realName").textValue());
                        ccrUser.setPassWord(password);
                        ccrUser.setRules(Sets.newHashSet(ruleRepository.getOne(3L)));
                        userRepository.save(ccrUser);
                    }
                }catch (Exception e){
                    errerUserCode.append(jsonNode.get("studentNo").textValue()+",");
                    logger.error(e.getMessage(),e);
                    continue;
                }

            }
        }
        return errerUserCode.toString();
    }

    /*private void executeSql(StringBuffer userCodeString, StringBuffer userGroupString,StringBuffer userRuleString,String password){
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
    }*/

    /**
     * 保存上传的文件
     *
     * @return 保存的路径
     */
    public String saveUploadFile(MultipartFile file, String... supportFormats) throws Exception {
        String format = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        if (supportFormats.length!=0 && !Lists.newArrayList(supportFormats).contains(format)) {
            throw new Exception("格式不支持:" + format);
        }

        File outPutFile = new File(new StringBuilder().append(UPLOAD_FILE_PATH)
                .append(DigestUtils.md2Hex(String.valueOf(System.currentTimeMillis())))
                .append(".")
                .append(format)
                .toString());

        InputStream is = null;

        try {
            is = file.getInputStream();

            FileUtils.copyInputStreamToFile(is, outPutFile);
        } finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return outPutFile.getPath();
    }

    /**
     * 设置图片访问地址
     */
    public CcrUser setIconURL(CcrUser user) {
        if (user.getHeadPath()!=null) {
            String url = user.getHeadPath()
                    .replace(".\\upload\\user\\","/upload/user/");

            user.setHeadUrl(url);
        }
        return user;
    }


}
