package com.lqkj.web.cmccr2.modules.menu.service;

import com.alibaba.fastjson.JSON;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.menu.dao.CcrMenuSQLDao;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import com.lqkj.web.cmccr2.modules.menu.dao.CcrMenuRepository;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 菜单管理服务
 */
@Service
@Transactional
public class MenuService {

    @Autowired
    CcrMenuRepository menuDao;

    @Autowired
    CcrVersionApplicationRepository managerApplicationDao;

    @Autowired
    CcrSystemLogService systemLogService;

    @Autowired
    CcrMenuSQLDao menuSQLDao;

    /**
     * 装机菜单
     */
    public Long createMenu(CcrMenu menu) {
        systemLogService.addLog("菜单管理服务", "createMenu"
                , "创建菜单");

        menu.setStatus(Boolean.TRUE);
        //先根据菜单名称查看是否已经存在
        String menuName = menu.getName();
        Boolean exits = menuDao.nameByMenu(menuName).isEmpty();
        CcrMenu cm = null;
        if (!exits) {
            return null;
        } else {
            cm = setOrder(menu);
        }
        return menuDao.save(cm).getMenuId();
    }


    /**
     * 删除应用
     */
    public void deleteMenu(Long[] id) {
        systemLogService.addLog("菜单管理服务", "deleteMenu"
                , "删除菜单");

        for (Long i : id) {
            CcrMenu menu = menuDao.getOne(i);
            menuDao.delete(menu);
        }
    }

    /**
     * 得到根节点
     */
    public CcrMenu info(Long id) {
        systemLogService.addLog("菜单管理服务", "info"
                , "查询菜单信息");

        return menuDao.findById(id).get();
    }

    /**
     * 更新节点
     */
    public CcrMenu update(Long id, CcrMenu menu) {
        systemLogService.addLog("菜单管理服务", "update"
                , "更新菜单信息");
        menu.setUpdateTime(new Timestamp(new Date().getTime()));
        CcrMenu cm = setOrder(menu);
        String[] specifyUserId = menu.getSpecifyUserId();
        String[] targetUserRole = menu.getTargetUserRole();
        ArrayList<CcrMenu> parentCcrMenus = new ArrayList<>();
        queryParentMenu(menu, parentCcrMenus);
        //添加父级菜单权限
        if (parentCcrMenus != null && parentCcrMenus.size() > 0) {
            for (CcrMenu ccrMenu : parentCcrMenus) {
                
                String[] parentSpecifyUserId = ccrMenu.getSpecifyUserId();
                String[] parentTargetUserRole = ccrMenu.getTargetUserRole();
                
                List<String> parentSpecifyUserIdList = Arrays.asList(parentSpecifyUserId);
                List<String> parentTargetUserRoleList = Arrays.asList(parentTargetUserRole);

                if (parentSpecifyUserIdList.indexOf("public") < 0) {
                    for (String userId : specifyUserId) {
                        if(parentSpecifyUserIdList.indexOf(userId) < 0){
                            parentSpecifyUserIdList.add(userId);
                        }
                    }
                }
                if (parentTargetUserRoleList.indexOf("public") < 0) {
                    for (String role : targetUserRole) {
                        if(parentTargetUserRoleList.indexOf(role) < 0){
                            parentTargetUserRoleList.add(role);
                        }
                    }
                }
                ccrMenu.setSpecifyUserId(parentSpecifyUserIdList.toArray(new String[parentSpecifyUserIdList.size()]));
                ccrMenu.setTargetUserRole(parentTargetUserRoleList.toArray(new String[parentTargetUserRoleList.size()]));
                menuDao.save(ccrMenu);
            }
        }
        //添加子级菜单权限
        ArrayList<CcrMenu> chCcrMenus = new ArrayList<>();
        querychildMenus(menu,chCcrMenus);
        if(chCcrMenus!=null && chCcrMenus.size()>0){
            for (CcrMenu ccrMenu : chCcrMenus) {

                String[] chSpecifyUserId = ccrMenu.getSpecifyUserId();
                String[] chTargetUserRole = ccrMenu.getTargetUserRole();

                List<String> chSpecifyUserIdList = Arrays.asList(chSpecifyUserId);
                List<String> chTargetUserRoleList = Arrays.asList(chTargetUserRole);

                if (chSpecifyUserIdList.indexOf("public") < 0) {
                    for (String userId : specifyUserId) {
                        if(chSpecifyUserIdList.indexOf(userId) < 0){
                            chSpecifyUserIdList.add(userId);
                        }
                    }
                }
                if (chTargetUserRoleList.indexOf("public") < 0) {
                    for (String role : targetUserRole) {
                        if(chTargetUserRoleList.indexOf(role) < 0){
                            chTargetUserRoleList.add(role);
                        }
                    }
                }
                ccrMenu.setSpecifyUserId(chSpecifyUserIdList.toArray(new String[chSpecifyUserIdList.size()]));
                ccrMenu.setTargetUserRole(chTargetUserRoleList.toArray(new String[chTargetUserRoleList.size()]));
                menuDao.save(ccrMenu);
            }
        }
        return menuDao.save(cm);
    }

    /**
     * 分页查询
     */
    public Page<CcrMenu> page(String keyword, String roles, String userCode, Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "page"
                , "分页查询菜单信息");
        String sql = "select * from ccr_menu where 1=1 ";

        if (keyword != null) {
            sql += "and name like '%" + keyword + "%' ";
        }

        if (userCode != null && roles != null) {
            if ("".equals(roles)) {
                sql += " and (target_user_role && ARRAY['','public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            } else {
                sql += " and (target_user_role && ARRAY[" + roles + ",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("sort"));

        return menuSQLDao.execQuerySqlPage(pageable, sql, null, CcrMenu.class);

    }

    /**
     * 按照类型分页查询
     */
    public Page<CcrMenu> typePage(CcrMenu.IpsMenuType type, String keyword, String roles, String userCode, Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "typePage"
                , "按照类型分页查询菜单信息");

        String sql = "select * from ccr_menu where status=true ";

        if (keyword != null) {
            sql += "and name like '%" + keyword + "%' ";
        }

        if (type != null) {
            sql += " and type = '" + type + "' ";
        }

        if (userCode != null && roles != null) {
            if ("".equals(roles)) {
                sql += " and (target_user_role && ARRAY['','public'] \\:\\: varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            } else {
                sql += " and (target_user_role && ARRAY[" + roles + ",'public'] \\:\\: varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\: varchar[]) ";
            }
        }
        sql += "order by sort";

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("sort"));

        return menuSQLDao.execQuerySqlPage(pageable, sql, null, CcrMenu.class);

    }

    /**
     * 排序
     */
    private CcrMenu setOrder(CcrMenu menu) {
        List<CcrMenu> list = menuDao.findAll();
        if (list.size() > 0) {
            if (menu.getSort() == null || menu.getSort() > list.size() || menu.getSort() == 0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSort(i + 1);
                    menuDao.save(list.get(i));
                }
                menu.setSort(list.size() + 1);
            }
            if (1 == menu.getSort()) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSort(i + 2);
                    menuDao.save(list.get(i));
                }
            }
            if (menu.getSort() > 1 && menu.getSort() <= list.size()) {
                for (int i = 0; i < menu.getSort() - 1; i++) {
                    list.get(i).setSort(i + 1);
                    menuDao.save(list.get(i));
                }
                for (int i = menu.getSort() - 1; i < list.size(); i++) {
                    list.get(i).setSort(i + 2);
                    menuDao.save(list.get(i));
                }
            }
        } else {
            menu.setSort(1);
        }
        return menu;
    }

    /***
     * 查询所有父级菜单
     */
    public void queryParentMenu(CcrMenu menu, List<CcrMenu> parentMenus) {
        Long parentId = menu.getParentId();
        CcrMenu parentMenu = menuDao.getOne(parentId);
        if (parentMenu != null) {
            parentMenus.add(parentMenu);
            if (parentMenu.getParentId() != null) {
                queryParentMenu(parentMenu, parentMenus);
            }
        }
    }
    /***
     * 查询所有子级菜单
     */
    public void querychildMenus(CcrMenu menu, List<CcrMenu> childMenus) {
        Long menuId = menu.getMenuId();
        List<CcrMenu> childMs = menuDao.childMenus(menuId);
        if (childMs != null && childMenus.size()>0) {
            childMenus.addAll(childMs);
            for (CcrMenu ccrMenu:childMs) {
                querychildMenus(ccrMenu,childMenus);
            }
        }
    }

    public static void main(String[] args) {
        String[] arr2 = {"2", "4", "6", "8", "10"};
        String[] arr1 = {"2", "4", "1", "2", "10"};
        List<String> asList = Arrays.asList(arr2);
        int i = asList.indexOf("2");
        System.out.println(i);
    }

    public String authAllMenu(String roles, String userCode) {
        String sql = "select * from ccr_menu where status=true ";

        if (userCode != null && roles != null) {
            if ("".equals(roles)) {
                sql += " and (target_user_role && ARRAY['','public'] \\:\\: varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            } else {
                sql += " and (target_user_role && ARRAY[" + roles + ",'public'] \\:\\: varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\: varchar[]) ";
            }
        }
        sql += "order by sort";
        List<CcrMenu> ccrMenus = menuSQLDao.executeSql(sql, CcrMenu.class);
        List<CcrMenu> menuTree = new ArrayList<>();
        if(ccrMenus!=null && ccrMenus.size()>0){
            for (CcrMenu ccrMenu:ccrMenus) {
                if(ccrMenu.getParentId()==null){
                    menuTree.add(ccrMenu);
                    findChMenu(ccrMenus,ccrMenu);
                }
            }
        }

        return JSON.toJSONString(menuTree);
    }

    private void findChMenu(List<CcrMenu> ccrMenus,CcrMenu menu){
        Set<CcrMenu> chMenu = new HashSet<CcrMenu>();
        menu.setChCcrMenu(chMenu);
        for (CcrMenu ccrMenu:ccrMenus) {
            if(ccrMenu.getParentId()!=null && ccrMenu.getParentId().equals(menu.getMenuId())){
                chMenu.add(ccrMenu);
                findChMenu(ccrMenus,ccrMenu);
            }
        }
    }
}
