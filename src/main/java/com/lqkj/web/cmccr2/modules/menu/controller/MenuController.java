package com.lqkj.web.cmccr2.modules.menu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.menu.service.MenuService;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import io.swagger.annotations.*;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "菜单")
@RestController
public class MenuController {

    public static final String VERSION = "v1";

    @Autowired
    MenuService menuService;
    @Autowired
    CcrUserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("创建菜单")
    @PutMapping("/center/menu/" + VERSION + "/create")
    public MessageBean<Long> create(@RequestBody CcrMenu ccrMenu) {
        //先判断菜单名称是否重复
        /*if(menuService.createMenu(ccrMenu)!=null){
            return MessageBean.ok(menuService.createMenu(ccrMenu));
        }
        return MessageBean.error("存在相同的菜单名称");*/
        try {
            return MessageBean.ok(menuService.createMenu(ccrMenu));
        }catch (Exception e){
            logger.error("菜单创建失败",e);
            return MessageBean.error("菜单创建失败");
        }
    }

    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "菜单id")
    @DeleteMapping("/center/menu/" + VERSION + "/delete/{id}")
    public MessageBean<Object> delete(@PathVariable("id") Long[] id) throws Exception {
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
    public WebAsyncTask<MessageBean<Page<CcrMenu>>> search(String keyword, Integer page, Integer pageSize,Authentication authentication) {
       // String[] userRole = new String[]{"",""};
        List<String> userRole = getRolesAndCode(authentication);
        return new WebAsyncTask<>(() ->MessageBean.ok(menuService.page(keyword,userRole.get(0),userRole.get(1), page, pageSize)));
    }

    /**
     * 根据权限查询菜单
     * @param authentication
     * @return
     */
    @ApiOperation("根据权限查询菜单")
    @GetMapping("/center/menu/" + VERSION + "/auth/all")
    public WebAsyncTask<MessageBean<com.alibaba.fastjson.JSONArray>> authAllMenu(@ApiIgnore Authentication authentication, @RequestParam(required = false) CcrMenu.AppType type){
        List<String> userRole = getRolesAndCode(authentication);
        return new WebAsyncTask<>(() -> MessageBean.ok(JSON.parseArray(menuService.authAllMenu(userRole.get(0), userRole.get(1),type))));
    }



    @ApiOperation("查询菜单项信息")
    @ApiImplicitParam(name = "id", value = "菜单id")
    @GetMapping("/center/menu/" + VERSION + "/info/{id}")
    public WebAsyncTask<MessageBean<CcrMenu>> info(@PathVariable("id") Long id) {
        return new WebAsyncTask<>(() -> MessageBean.ok(menuService.info(id)));
    }

    @ApiOperation("按照类型分页查询菜单列表")
    @GetMapping(value = {"/center/menu/" + VERSION + "/page/{type}/",
            "/center/menu/" + VERSION + "/page"})
    public WebAsyncTask<MessageBean<Page<CcrMenu>>> typePage(@RequestParam Integer page,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String keyword,
                                                             @PathVariable(required = false) CcrMenu.IpsMenuType type,
                                                             @ApiIgnore Authentication authentication) {
        List<String> userRole = getRolesAndCode(null);
        return new WebAsyncTask<>(() -> MessageBean.ok(menuService.typePage(type, keyword,userRole.get(0),userRole.get(1), page, pageSize)));
    }

    @ApiOperation("修改菜单权限用户接口")
    @PutMapping("/center/menu/" + VERSION + "/updateSpecifyUser")
    public WebAsyncTask<MessageBean<CcrMenu>> info(
            @ApiParam(name = "ename",value = "菜单英文名称",required = true)@RequestParam(required = true) String ename,
            @ApiParam(name = "operateType",value = "操作方式：delete,add",required = true)@RequestParam(required = true) String operateType,
            @ApiParam(name = "userCodes",value = "用户Code",required = true)@RequestParam(required = true) String[] userCodes) {
        return new WebAsyncTask<>(() -> MessageBean.ok(menuService.updateSpecifyUser(ename,operateType,userCodes)));
    }


    private List<String> getRolesAndCode(Authentication authentication){
        List<String> userRole = new ArrayList<>();
        String roles = "";
        String userCode = "";
        if(authentication != null && authentication.getAuthorities().size()!=0) {

            Jwt jwt = (Jwt) authentication.getPrincipal();

            String userName = (String) jwt.getClaims().get("user_name");
            //String userName = null;
            if(userName != null){
                userCode = userService.findByUserName(userName).getUserId().toString();
            }

            JSONArray jsonArray = (JSONArray) jwt.getClaims().get("rules");
            if(jsonArray != null && jsonArray.size() > 0){
                List<String> list = JSONObject.parseArray(jsonArray.toJSONString(), String.class);
                if(list.size() > 0 && !"".equals(list.get(0))){
                    roles = "'" + list
                            .stream()
                            .collect(Collectors.joining("','")) + "'";
                }
            }else {
                if(userName != null){
                    roles  = "'" + userService.findByUserName(userName).getRules().stream()
                            .map(CcrUserRule::getContent)
                            .collect(Collectors.joining("','")) + "'";
                }
            }
        }
        userRole.add(roles);
        userRole.add(userCode);

        return userRole;
    }
}
