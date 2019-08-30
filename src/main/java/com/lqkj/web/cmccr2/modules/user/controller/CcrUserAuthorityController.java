package com.lqkj.web.cmccr2.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "用户权限")
@RestController
@Validated
public class CcrUserAuthorityController {

    @Autowired
    CcrUserAuthorityService authorityService;

    @ApiOperation("增加用户权限")
    @PutMapping("/center/user/authority/")
    public MessageBean<CcrUserAuthority> add(CcrUserAuthority authority) {
        return MessageBean.ok(authorityService.add(authority));
    }

    @ApiOperation("删除用户权限")
    @DeleteMapping("/center/user/authority/{id}")
    public MessageBean<Long[]> delete(Long[] id) {
        authorityService.delete(id);
        return MessageBean.ok(id);
    }

    @ApiOperation("更新用户权限")
    @PostMapping("/center/user/authority/{id}")
    public MessageBean<CcrUserAuthority> update(@RequestBody CcrUserAuthority authority,
                                                @PathVariable Long id) {
        return MessageBean.ok(authorityService.update(id, authority));
    }

    @ApiOperation("查询用户权限")
    @GetMapping("/center/user/authority/{id}")
    public MessageBean<CcrUserAuthority> info(@PathVariable Long id) {
        return MessageBean.ok(authorityService.info(id));
    }

    @ApiOperation("分页查询用户权限")
    @GetMapping("/center/user/authority/page")
    public MessageBean<Page<CcrUserAuthority>> page(String keyword,
                                                    @RequestParam Integer page,
                                                    @RequestParam Integer pageSize) {
        return MessageBean.ok(authorityService.page(keyword, page, pageSize));
    }

    @ApiOperation("根据角色id查询权限")
    @GetMapping("/center/user/authority")
    public MessageListBean<CcrUserAuthority> queryByRule(@RequestParam Long ruleId) {
        return MessageListBean.ok(authorityService.findByRuleId(ruleId));
    }

    @ApiOperation("根据类型查询权限")
    @GetMapping("/center/user/authority/type/{type}")
    public MessageListBean<CcrUserAuthority> queryByType(@PathVariable CcrUserAuthority.UserAuthorityType type) {
        return MessageListBean.ok(authorityService.findByType(type));
    }

    @ApiOperation("匹配更新权限是否开启")
    @PostMapping("/center/user/authority/batch/enabled")
    public MessageBean batchUpdateEnabled(@RequestParam Long[] authorities,
                                          @RequestParam Boolean enabled) {
        this.authorityService.batchUpdateEnabled(authorities, enabled);
        return MessageBean.ok();
    }

    @ApiOperation("获取权限状态列表")
    @PostMapping("/center/user/authority/list")
    public MessageBean<List<CcrUserAuthority>> findByRoleAndUserId(Authentication authentication) {
        String rules = "";
        String userCode = "";
        if(authentication != null){
            Jwt jwt =(Jwt)authentication.getPrincipal();
            JSONArray jsonArray = (JSONArray)jwt.getClaims().get("rules");
            List<String> list  = JSONObject.parseArray(jsonArray.toJSONString(),String.class);
            rules = "'" + list
                    .stream()
                    .collect(Collectors.joining("','")) + "'";
            userCode = (String)jwt.getClaims().get("user_name");
        }
        return MessageBean.ok(authorityService.findByRoleAndUserId(userCode, rules));
    }
}
