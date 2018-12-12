package com.lqkj.web.cmccr2.modules.user.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@Validated
public class CcrUserController {

    @Autowired
    CcrUserService ccrUserService;

    @ApiOperation("注册用户")
    @PutMapping("/center/user/register")
    public MessageBean<CcrUser> register(@RequestBody CcrUser user, @RequestParam Integer adminCode) throws Exception {
        return MessageBean.ok(ccrUserService.registerAdmin(adminCode, user));
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/center/user/{id}")
    public MessageBean<CcrUser> info(@PathVariable Long id) {
        return MessageBean.ok(ccrUserService.info(id));
    }

    @ApiOperation("更新用户密码")
    @PostMapping("/center/user/{id}")
    public MessageBean<String> updatePassword(@RequestParam String password, @PathVariable Long id) {
        return MessageBean.ok(ccrUserService.updatePassword(id, password));
    }

    @ApiOperation("根据用户id删除用户")
    @DeleteMapping("/center/user/{id}")
    public MessageBean<Long> delete(@PathVariable Long id) {
        ccrUserService.delete(id);
        return MessageBean.ok();
    }

    @ApiOperation("分页查询用户信息")
    @GetMapping("/center/user/")
    public MessageBean<Page<CcrUser>> page(String keyword, Integer page, Integer pageSize) {
        return MessageBean.ok(ccrUserService.page(keyword, page, pageSize));
    }

    @ApiOperation("根据用户id查询用户角色")
    @GetMapping("/center/user/{id}/rules")
    public MessageListBean<CcrUserRule> ruleByUserId(@PathVariable Long id) {
        return MessageListBean.ok(ccrUserService.findRulesByUserId(id));
    }
}
