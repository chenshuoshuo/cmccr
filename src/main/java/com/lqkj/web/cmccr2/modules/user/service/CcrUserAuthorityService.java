package com.lqkj.web.cmccr2.modules.user.service;

import com.google.common.collect.Lists;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.menu.service.MenuService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthoritySQLDao;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRuleRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户权限服务
 */
@Service
@Transactional
public class CcrUserAuthorityService {

    @Autowired
    private CcrUserAuthoritySQLDao userAuthoritySQLDao;

    private CcrUserAuthorityRepository userAuthorityRepository;

    private CcrUserRuleRepository userRuleRepository;

    private CcrSystemLogService systemLogService;

    @Autowired
    private MenuService menuService;

    public CcrUserAuthorityService(CcrUserAuthorityRepository userAuthorityRepository,
                                   CcrUserRuleRepository userRuleRepository,
                                   CcrSystemLogService systemLogService) {
        this.userAuthorityRepository = userAuthorityRepository;
        this.userRuleRepository = userRuleRepository;
        this.systemLogService = systemLogService;
    }

    public CcrUserAuthority add(CcrUserAuthority authority) {
        systemLogService.addLog("用户权限服务", "add",
                "增加一个用户权限");

        authority.setEnabled(Boolean.TRUE);
        CcrUserAuthority saveAuthority = userAuthorityRepository.save(authority);

        CcrUserRule saveRule = userRuleRepository.getOne(1L);
        saveRule.getAuthorities().add(saveAuthority);
        userRuleRepository.save(saveRule);

        return saveAuthority;
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

        HashSet<CcrUserAuthority> ccrUserAuthorities = new HashSet<>();

        queryChildAuth(ccrUserAuthorities, id);


        if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
            Iterator<CcrUserAuthority> it = ccrUserAuthorities.iterator();
            while (it.hasNext()) {
                CcrUserAuthority auth = it.next();
                userAuthorityRepository.updateChildState(auth.getAuthorityId(), savedAuthority.getEnabled());
            }
        }

        if (savedAuthority.getParentId() != null) {
            ccrUserAuthorities.clear();

            queryParentAuth(ccrUserAuthorities, savedAuthority.getParentId());

            if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
                Iterator<CcrUserAuthority> it = ccrUserAuthorities.iterator();
                while (it.hasNext()) {
                    CcrUserAuthority auth = it.next();
                    userAuthorityRepository.updateChildState(auth.getAuthorityId(), savedAuthority.getEnabled());
                }
            }
        }

        //关闭根据英文名进行匹配关闭菜单里面的功能
        String content = savedAuthority.getContent();

        menuService.updateMenuStatus(content, savedAuthority.getEnabled());

        return userAuthorityRepository.save(savedAuthority);
    }

    public void queryChildAuth(HashSet<CcrUserAuthority> ccrUserAuths, Long id) {
        List<CcrUserAuthority> ccrUserAuthorities = userAuthorityRepository.queryChildAuth(id);
        if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
            for (CcrUserAuthority ccrUserAuthority : ccrUserAuthorities) {
                ccrUserAuths.add(ccrUserAuthority);
                queryChildAuth(ccrUserAuths, ccrUserAuthority.getAuthorityId());
            }
        }
    }

    public void queryParentAuth(HashSet<CcrUserAuthority> ccrUserAuths, Long parentId) {
        CcrUserAuthority parentAuth = userAuthorityRepository.getOne(parentId);
        if (parentAuth != null) {
            ccrUserAuths.add(parentAuth);
            queryParentAuth(ccrUserAuths, parentAuth.getParentId());
        }
    }

    public CcrUserAuthority info(Long id) {
        systemLogService.addLog("用户权限服务", "info",
                "查询一个用户权限");

        return userAuthorityRepository.findById(id).get();
    }

    public Page<CcrUserAuthority> page(String name, String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("用户权限服务", "page",
                "分页查询用户权限");
        String k = keyword == null ? "" : keyword;

        return userAuthorityRepository.findSupportAuthority(name, "%" + k + "%",
                PageRequest.of(page, pageSize));
    }

    public List<CcrUserAuthority> findByRuleId(Long ruleId) {
        systemLogService.addLog("用户权限服务", "findByRuleId",
                "根据角色查询权限");

        return Lists.newArrayList(userRuleRepository.getOne(ruleId).getAuthorities());
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

    public List<CcrUserAuthority> findByRoleAndUserId(String userId, String roles) {
        systemLogService.addLog("用户权限服务", "findByRoleAndUserId",
                "查询更新权限状态列表");

        String sql = "select * from ccr_user_authority " +
                " where 1 = 1";

        if (userId != null && roles != null) {
            sql += " and target_user_role && ARRAY[" + roles + ",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userId + "'] \\:\\:varchar[] group by authority_id";
        }

        return userAuthoritySQLDao.executeSql(sql, CcrUserAuthority.class);
    }
}
