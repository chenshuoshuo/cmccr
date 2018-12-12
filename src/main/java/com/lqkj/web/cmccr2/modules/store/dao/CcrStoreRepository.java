package com.lqkj.web.cmccr2.modules.store.dao;

import com.lqkj.web.cmccr2.modules.store.domain.CcrStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrStoreRepository extends JpaRepository<CcrStore,Long> {
    CcrStore findByNameEquals(String name);

    boolean existsByNameEquals(String name);

    void deleteByNameEquals(String name);
}
