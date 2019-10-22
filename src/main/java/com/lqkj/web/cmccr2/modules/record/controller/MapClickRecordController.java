package com.lqkj.web.cmccr2.modules.record.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import com.lqkj.web.cmccr2.modules.record.serivce.MapClickRecordService;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

@Api(tags = "地图点击记录")
@RestController
public class MapClickRecordController {

    private MapClickRecordService mapClickRecordService;

    @Autowired
    CcrUserService userService;

    public MapClickRecordController(MapClickRecordService mapClickRecordService) {
        this.mapClickRecordService = mapClickRecordService;
    }

    @ApiOperation("增加点击地图记录")
    @PutMapping("/center/click/map/" + APIVersion.V1 + "/add")
    public WebAsyncTask<Void> addRecord(@RequestBody CcrMapClickRecord clickRecord,
                                        Authentication authentication) {
        if(authentication == null){
            clickRecord.setUserGroup("guest");
        } else {
            Jwt jwt =(Jwt)authentication.getPrincipal();
            if(jwt.getClaims().get("user_name") == null){
                clickRecord.setUserGroup("guest");
            }else {
                String userCode = (String)jwt.getClaims().get("user_name");
                CcrUser ccrUser = userService.findByUserCode(userCode);
                if(ccrUser != null){
                    if(ccrUser.getUserGroup() == null){
                        clickRecord.setUserGroup("manager");
                    } else {
                        clickRecord.setUserGroup(ccrUser.getUserGroup().toString());
                    }
                }else {
                    clickRecord.setUserGroup("manager");
                }
            }
        }
        return new WebAsyncTask<>(() -> {
            mapClickRecordService.add(clickRecord);
            return null;
        });
    }

    @ApiOperation("查询地图点击接口")
    @GetMapping("/center/click/map/" + APIVersion.V1 + "/record")
    public MessageListBean<Object[]> clickRecord() {
        return MessageListBean.ok(mapClickRecordService.clickRecord());
    }

    @ApiOperation("根据用户组查询地图点击接口")
    @GetMapping("/center/click/map/" + APIVersion.V1 + "/recordByGroup")
    public MessageListBean<Object[]> clickRecordByGroup(@ApiParam(name = "userGroup",value = "用户组（全部、学生、教师、其他）")
                                                            @RequestParam(name = "userGroup",defaultValue = "全部",required = true) String userrGroup) {
        return MessageListBean.ok(mapClickRecordService.clickRecordByGroup(userrGroup));
    }
}
