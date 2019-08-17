package com.lqkj.web.cmccr2.modules.user.controller;

import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户角色")
@RestController
@Validated
public class CcrUserRuleController {

    @Autowired
    CcrUserRuleService ruleService;

    @ApiOperation("增加角色")
    @PutMapping("/center/user/rule")
    public MessageBean<CcrUserRule> add(@RequestParam String name,
                                        @RequestParam String enName,
                                        @RequestParam Long[] authorities) {
        Object object=ruleService.add(name, enName, authorities);
        if(object!=null){
            return MessageBean.ok((CcrUserRule) object);
        }
        return MessageBean.error("存在相同角色名");
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/center/user/rule/{id}")
    public MessageBean<Long[]> delete(@PathVariable Long[] id) {
        ruleService.delete(id);
        return MessageBean.ok(id);
    }

    @ApiOperation("更新用户角色")
    @PostMapping("/center/user/rule/{id}")
    public MessageBean<CcrUserRule> update(@RequestParam String name,
                                           @RequestParam String enName,
                                           @RequestParam Long[] authorities,
                                           @PathVariable Long id) {
        return MessageBean.ok(ruleService.update(id, name, enName, authorities));
    }

    @ApiOperation("查询用户角色信息")
    @GetMapping("/center/user/rule/{id}")
    public MessageBean<CcrUserRule> info(@PathVariable Long id) {
        return MessageBean.ok(ruleService.info(id));
    }

    @ApiOperation("分页查询用户角色")
    @GetMapping("/center/user/rule")
    public MessageBean<Page<CcrUserRule>> page(@RequestParam(required = false) String keyword,
                                               @RequestParam Integer page,
                                               @RequestParam Integer pageSize) {
        return MessageBean.ok(ruleService.page(keyword, page, pageSize));
    }
}
