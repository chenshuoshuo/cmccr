package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrUserRuleRepository extends JpaRepository<CcrUserRule, Long> {
}
