package com.lqkj.web.cmccr2.modules.menu.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
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
        if(!exits){
            return null;
        }
        return menuDao.save(menu).getMenuId();
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
        return menuDao.save(menu);
    }

    /**
     * 分页查询
     */
    public Page<CcrMenu> page(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "page"
                , "分页查询菜单信息");

        CcrMenu menu = new CcrMenu();
        menu.setName(keyword);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");

        return menuDao.findAll(Example.of(menu, matcher),
                PageRequest.of(page, pageSize, Sort.by("sort")));
    }

    /**
     * 按照类型分页查询
     */
    public Page<CcrMenu> typePage(CcrMenu.IpsMenuType type, String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("菜单管理服务", "typePage"
                , "按照类型分页查询菜单信息");

        CcrMenu menu = new CcrMenu();
        menu.setType(type);
        menu.setName(keyword);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");

        if (type!=null) {
            matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        return menuDao.findAll(Example.of(menu, matcher),
                PageRequest.of(page, pageSize, Sort.by("sort")));
    }
}
