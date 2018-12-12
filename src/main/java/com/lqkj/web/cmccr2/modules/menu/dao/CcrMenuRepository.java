package com.lqkj.web.cmccr2.modules.menu.dao;

import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrMenuRepository extends JpaRepository<CcrMenu, Long> {
}
