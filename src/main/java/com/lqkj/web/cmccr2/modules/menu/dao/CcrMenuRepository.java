package com.lqkj.web.cmccr2.modules.menu.dao;

import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface CcrMenuRepository extends JpaRepository<CcrMenu, Long> {

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")
    })
    @Override
    <S extends CcrMenu> Page<S> findAll(Example<S> example, Pageable pageable);
}
