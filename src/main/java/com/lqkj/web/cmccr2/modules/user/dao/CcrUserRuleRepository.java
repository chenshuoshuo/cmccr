package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrUserRuleRepository extends JpaRepository<CcrUserRule, Long> {

    @Query(nativeQuery = true, value = "select r.* from ccr_user_rule r " +
            "inner join ccr_user_to_rule ur on r.rule_id = ur.rule_id " +
            "inner join ccr_user u on u.user_id = ur.user_id " +
            "where u.user_code=:username and r.name like :keyword group by r.rule_id")
    Page<CcrUserRule> findSupportRules(@Param("username") String username,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);
}
