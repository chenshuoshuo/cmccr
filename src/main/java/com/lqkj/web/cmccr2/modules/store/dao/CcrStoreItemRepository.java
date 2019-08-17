package com.lqkj.web.cmccr2.modules.store.dao;

import com.lqkj.web.cmccr2.modules.store.domain.CcrStore;
import com.lqkj.web.cmccr2.modules.store.domain.CcrStoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface CcrStoreItemRepository extends JpaRepository<CcrStoreItem,Long> {
    boolean existsByKeyEqualsAndStoreEquals(String key, CcrStore store);

    void deleteByKeyEqualsAndStoreEquals(String key, CcrStore store);

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")
    })
    @Query("select i from CcrStoreItem i where i.store.name=:name and i.key=:key")
    CcrStoreItem findByNameAndKey(@Param("name") String name,
                                 @Param("key") String key);
}
