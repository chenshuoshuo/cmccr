package com.lqkj.web.cmccr2.modules.user.controller;

import com.google.zxing.WriterException;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.application.service.MultiApplicationService;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import com.lqkj.web.cmccr2.modules.user.service.WeiXinOAuthService;
import com.lqkj.web.cmccr2.utils.ServletUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Api(tags = "用户管理")
@RestController
@Validated
public class CcrUserController {

    @Autowired
    CcrUserService ccrUserService;

    @Autowired
    WeiXinOAuthService weiXinOAuthService;

    @Autowired
    MultiApplicationService multiApplicationService;

    @ApiOperation("注册用户")
    @PutMapping("/center/user/register")
    public MessageBean<CcrUser> register(@RequestBody CcrUser user, @RequestParam Integer adminCode) throws Exception {
        if(ccrUserService.registerAdmin(adminCode, user)!=null) {
            return MessageBean.ok(ccrUserService.registerAdmin(adminCode, user));
        }
        return MessageBean.error("用户已存在");
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/center/user/{id}")
    public MessageBean<CcrUser> info(@PathVariable Long id) {
        return MessageBean.ok(ccrUserService.info(id));
    }

    @ApiOperation("根据用户名查询用户信息")
    @GetMapping("/center/user/name/{username}")
    public MessageBean<CcrUser> info(@PathVariable String username,@ApiIgnore Authentication authentication) throws Exception {

        //String name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = "";
        if(authentication != null){
            Jwt jwt =(Jwt)authentication.getPrincipal();
            name = (String)jwt.getClaims().get("user_name");
        }

        return MessageBean.ok((CcrUser) ccrUserService.loadUserByUsername(name));
    }

    @ApiOperation("更新用户密码")
    @PostMapping("/center/user/{id}")
    public MessageBean<CcrUser> update(@RequestParam(required = false) String password,
                                      @RequestParam(required = false) Boolean admin,
                                      @RequestParam(required = false) String oldPassword,
                                      @PathVariable Long id,
                                      @ApiParam(value = "头像文件") MultipartFile headFile)throws Exception{

        String headPath = ccrUserService.saveUploadFile(headFile,"png", "jpg");
        return MessageBean.ok(ccrUserService.update(id,password,oldPassword,admin,headPath));
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
        return MessageListBean.ok(new ArrayList<>(ccrUserService.findRulesByUserId(id)));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", allowableValues = "weixin")
    })
    @ApiOperation("oauth用户绑定")
    @GetMapping("/center/user/{platform}/auth")
    public MessageBean<String> auth(@ApiIgnore Authentication authentication,
                                    @ApiIgnore HttpServletRequest request,
                                    @PathVariable String platform) throws IOException, WriterException {
        String baseURL = ServletUtils.createBaseUrl(request);

        CcrUser user = (CcrUser) authentication.getPrincipal();

        return MessageBean.ok(multiApplicationService.createQRCode(null,
                weiXinOAuthService.createAuthorizeURL(baseURL, user.getUserId())));
    }

    @ApiOperation("用戶組統計")
    @GetMapping("/center/user/group")
    public MessageListBean<Object[]> group() {
        return MessageListBean.ok(ccrUserService.userGroup());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", allowableValues = "weixin")
    })
    @ApiOperation("oauth2回调")
    @GetMapping("/center/user/{platform}/callback")
    public void callback(@RequestParam(name = "user_id") Long userId,
                         @RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state) {

    }

    @ApiOperation("绑定用户角色")
    @PostMapping("/center/user/{userId}/rule/bind")
    public MessageBean bindRules(@PathVariable Long userId,
                                 @RequestParam Long[] rules) {
        this.ccrUserService.bindRules(userId, rules);
        return MessageBean.ok();
    }

    @ApiOperation("退出登录")
    @PostMapping("/center/user/loginout/{userId}")
    public MessageBean loginout(@PathVariable Long userId) {
        this.ccrUserService.loginout(userId);
        return MessageBean.ok();
    }

    @ApiOperation("从CMDBE更新用户信息")
    @PostMapping("/center/user/updateFromCmdbe")
    public MessageBean updateFromCmdbe() {
        ccrUserService.updateUserFromCmdbe();
        return MessageBean.ok();
//        return new WebAsyncTask<>(() -> {
//            this.ccrUserService.updateUserFromCmdbe();
//            return null;
//        });
    }
}
