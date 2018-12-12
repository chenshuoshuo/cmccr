package com.lqkj.web.cmccr2.modules.store.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.store.dao.CcrStoreRepository;
import com.lqkj.web.cmccr2.modules.store.dao.CcrStoreItemRepository;
import com.lqkj.web.cmccr2.modules.store.domain.CcrStore;
import com.lqkj.web.cmccr2.modules.store.domain.CcrStoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * k-v储存服务
 */
@Service
@Transactional
public class StoreService {

    @Autowired
    CcrStoreRepository storeDao;

    @Autowired
    CcrStoreItemRepository storeItemDao;

    @Autowired
    CcrSystemLogService systemLogService;

    /**
     * 插入内容
     *
     * @param storeName 储存名称
     * @param key       键名
     * @param value     键值
     */
    public Long put(String storeName, String key, String value) {
        systemLogService.addLog("k-v储存服务", "put",
                "插入内容");

        CcrStore store = createOrGetStore(storeName);
        return putStoreItem(store, key, value);
    }

    /**
     * 删除内容
     *
     * @param storeName 储存名称
     * @param key       键名
     */
    public void remove(String storeName, String key) {
        systemLogService.addLog("k-v储存服务", "remove",
                "删除内容");

        if (storeDao.existsByNameEquals(storeName)) {
            CcrStore store = storeDao.findByNameEquals(storeName);

            storeItemDao.deleteByKeyEqualsAndStoreEquals(key, store);
        }
    }

    /**
     * 得到或者创建储存对象
     *
     * @param storeName 储存名称
     * @return id
     */
    private CcrStore createOrGetStore(String storeName) {
        CcrStore store;

        if (storeDao.existsByNameEquals(storeName)) {
            store = storeDao.findByNameEquals(storeName);
        } else {
            store = storeDao.save(new CcrStore(storeName));
        }

        return store;
    }

    /**
     * 保存项
     *
     * @param store 储存对象
     * @param key   键名
     * @param value 键值
     * @return id
     */
    private Long putStoreItem(CcrStore store, String key, String value) {
        CcrStoreItem item = new CcrStoreItem(key, value, store);

        if (storeItemDao.existsByKeyEqualsAndStoreEquals(key, store)) {
            storeItemDao.deleteByKeyEqualsAndStoreEquals(key, store);
        }

        return storeItemDao.save(item).getId();
    }
}
