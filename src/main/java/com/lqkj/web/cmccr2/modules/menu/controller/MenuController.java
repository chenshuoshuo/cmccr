package com.lqkj.web.cmccr2.modules.menu.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags = "菜单")
@RestController
public class MenuController {

    public static final String VERSION = "v1";

    @Autowired
    MenuService menuService;

    @ApiOperation("创建菜单")
    @PutMapping("/center/menu/" + VERSION + "/create")
    public MessageBean<Long> create(CcrMenu CcrMenu) {
        return MessageBean.ok(menuService.createMenu(CcrMenu));
    }

    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "菜单id")
    @DeleteMapping("/center/menu/" + VERSION + "/delete/{id}")
    public MessageBean<Object> delete(@PathVariable("id") Long id) throws Exception {
        menuService.deleteMenu(id);
        return MessageBean.ok();
    }

    @ApiOperation("更新菜单项")
    @PostMapping("/center/menu/" + VERSION + "/update/{id}")
    public MessageBean<CcrMenu> update(@PathVariable Long id,
                                       @RequestBody CcrMenu CcrMenu) {
        return MessageBean.ok(menuService.update(id, CcrMenu));
    }

    @ApiOperation("搜索菜单项")
    @GetMapping("/center/menu/" + VERSION + "/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", paramType = "query", value = "关键字")
    })
    public MessageBean<Page<CcrMenu>> search(String keyword, Integer page, Integer pageSize) {
        return MessageBean.ok(menuService.page(keyword, page, pageSize));
    }

    @ApiOperation("查询菜单项信息")
    @ApiImplicitParam(name = "id", value = "菜单id")
    @GetMapping("/center/menu/" + VERSION + "/info/{id}")
    public MessageBean<CcrMenu> info(@PathVariable("id") Long id) {
        return MessageBean.ok(menuService.info(id));
    }

    @ApiOperation("分页查询菜单列表")
    @GetMapping("/center/menu/" + VERSION + "/page")
    public MessageBean<Page<CcrMenu>> page(@RequestParam Integer page,
                                           @RequestParam Integer pageSize,
                                           String keyword) {
        return MessageBean.ok(menuService.page(keyword, page, pageSize));
    }

    @ApiOperation("按照类型分页查询菜单列表")
    @GetMapping("/center/menu/" + VERSION + "/page/{type}")
    public MessageBean<Page<CcrMenu>> typePage(@RequestParam Integer page,
                                           @RequestParam Integer pageSize,
                                           @PathVariable CcrMenu.IpsMenuType type) {
        return MessageBean.ok(menuService.typePage(type, page, pageSize));
    }
}
