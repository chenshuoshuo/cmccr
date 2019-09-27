package com.lqkj.web.cmccr2.modules.menu.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.menu.dao.CcrMenuSQLDao;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import com.lqkj.web.cmccr2.modules.menu.dao.CcrMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        String menuName=menu.getName();
        Boolean exits=menuDao.nameByMenu(menuName).isEmpty();
        CcrMenu cm = null;
        if(!exits){
            return null;
        }else {
            cm  = setOrder(menu);
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

            if (menu.getType()!=null && !menu.getType().equals(CcrMenu.IpsMenuType.embed)) {
                menuDao.delete(menu);
            }
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
        return menuDao.save(cm);
    }

    /**
     * 分页查询
     */
    public Page<CcrMenu> page(String keyword, String roles,String userCode,Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "page"
                , "分页查询菜单信息");
        String sql = "select * from ccr_menu where 1=1 ";

        if(keyword != null ){
            sql += "and name like '%" + keyword + "%' ";
        }

        if(userCode != null && roles != null){
            if("".equals(roles)){
                sql += " and (target_user_role && ARRAY['','public'] \\:\\:varchar[] or specify_user_id && ARRAY['"+ userCode +"'] \\:\\:varchar[]) ";
            }else {
                sql += " and (target_user_role && ARRAY[" + roles + ",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            }
        }

        Pageable pageable = PageRequest.of(page,pageSize,Sort.by("sort"));

        return menuSQLDao.execQuerySqlPage(pageable,sql,null,CcrMenu.class);

    }

    /**
     * 按照类型分页查询
     */
    public Page<CcrMenu> typePage(CcrMenu.IpsMenuType type, String keyword, String roles,String userCode,Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "typePage"
                , "按照类型分页查询菜单信息");

        String sql = "select * from ccr_menu where 1=1 ";

        if(keyword != null ){
            sql += "and name like '%" + keyword + "%' ";
        }

        if(type != null){
            sql += " and type = '" + type + "' ";
        }

        if(userCode != null && roles != null){
            if("".equals(roles)){
                sql += " and (target_user_role && ARRAY['','public'] \\:\\:varchar[] or specify_user_id && ARRAY['"+ userCode +"'] \\:\\:varchar[]) ";
            }else {
                sql += " and (target_user_role && ARRAY[" + roles + ",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userCode + "'] \\:\\:varchar[]) ";
            }
        }


        Pageable pageable = PageRequest.of(page,pageSize,Sort.by("sort"));

        return menuSQLDao.execQuerySqlPage(pageable,sql,null,CcrMenu.class);

    }

    /**
     * 排序
     */
    private CcrMenu setOrder(CcrMenu menu){
        List<CcrMenu> list = menuDao.findAllByType(menu.getType());
        if(list.size() > 0){
            if(menu.getSort() == null || menu.getSort() > list.size() || menu.getSort() == 0 ){
                for(int i = 0; i < list.size();i++){
                    list.get(i).setSort(i+1);
                    menuDao.save(list.get(i));
                }
                menu.setSort(list.size()+1);
            }
            if(1 == menu.getSort()){
                for(int i = 0; i < list.size();i++){
                    list.get(i).setSort(i+2);
                    menuDao.save(list.get(i));
                }
            }
            if(menu.getSort() > 1 && menu.getSort() <= list.size()){
                for(int i = 0; i < menu.getSort()-1;i++){
                    list.get(i).setSort(i+1);
                    menuDao.save(list.get(i));
                }
                for(int i = menu.getSort()-1; i < list.size();i++){
                    list.get(i).setSort(i+2);
                    menuDao.save(list.get(i));
                }
            }
        }else{
            menu.setSort(1);
        }
        return menu;
    }
}
