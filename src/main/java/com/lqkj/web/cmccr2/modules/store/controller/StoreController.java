package com.lqkj.web.cmccr2.modules.store.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "数据字典")
@RestController
public class StoreController {

    @Autowired
    public StoreService storeService;

    @ApiOperation("存储一个键值对到服务器")
    @PutMapping("/center/store/" + APIVersion.V1 + "/put")
    public MessageBean<Long> put(@RequestParam String storeName,
                                 @RequestParam String key,
                                 @RequestParam String value) {
        Long id = storeService.put(storeName, key, value);
        return MessageBean.ok(id);
    }

    @DeleteMapping("/center/store/" + APIVersion.V1 + "/remove/{storeName}/{key}")
    public MessageBean remove(@PathVariable(name = "storeName") String storeName,
                              @PathVariable(name = "key") String key) {
        storeService.remove(storeName, key);
        return MessageBean.ok();
    }

    @GetMapping("/center/store/" + APIVersion.V1 + "/{storeName}/{key}")
    public MessageBean<String> get(@PathVariable String storeName, @PathVariable String key) {
        return MessageBean.ok(storeService.get(storeName, key));
    }
}
