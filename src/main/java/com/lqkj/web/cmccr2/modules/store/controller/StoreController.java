package com.lqkj.web.cmccr2.modules.store.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.modules.store.domain.CcrStoreItem;
import com.lqkj.web.cmccr2.modules.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Api(tags = "数据字典")
@RestController
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @ApiOperation("存储一个键值对到服务器")
    @PutMapping("/center/store/" + APIVersion.V1 + "/put")
    public MessageBean<Long> put(@RequestParam String storeName,
                                 @RequestParam String key,
                                 @RequestParam String value,
                                 @RequestParam(required = false, defaultValue = MediaType.APPLICATION_JSON_VALUE)
                                         String contentType) {
        Long id = storeService.put(storeName, key, value, contentType);
        return MessageBean.ok(id);
    }

    @ApiOperation("删除一个储存的键值对")
    @DeleteMapping("/center/store/" + APIVersion.V1 + "/remove/{storeName}/{key}")
    public MessageBean remove(@PathVariable(name = "storeName") String storeName,
                              @PathVariable(name = "key") String key) {
        storeService.remove(storeName, key);
        return MessageBean.ok();
    }

    @ApiOperation("查询键值对信息")
    @GetMapping("/center/store/" + APIVersion.V1 + "/{storeName}/{key}")
    public WebAsyncTask<ResponseEntity<Object>> get(@PathVariable String storeName,
                                                    @PathVariable String key) {
        return new WebAsyncTask<>(() -> {
            CcrStoreItem value = storeService.get(storeName, key);

            if (value != null && value.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                String digest = DigestUtils.md2Hex(value.getValue());

                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf(value.getContentType()))
                        .eTag("w/" + digest)
                        .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS))
                        .body(MessageBean.ok(value.getValue()));
            } else {
                byte[] bs = Base64.getDecoder().decode(value.getValue());

                String digest = DigestUtils.md2Hex(bs);

                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf(value.getContentType()))
                        .eTag("w/" + digest)
                        .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS))
                        .body(bs);
            }
        });
    }
}
