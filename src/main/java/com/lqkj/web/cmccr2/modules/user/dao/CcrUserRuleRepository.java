package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CcrUserRuleRepository extends JpaRepository<CcrUserRule, Long> {

    @Query(nativeQuery = true, value = "select r.* from ccr_user_rule r " +
            "inner join ccr_user_to_rule ur on r.rule_id = ur.rule_id " +
            "inner join ccr_user u on u.user_id = ur.user_id " +
            "where u.user_code=:username and r.name like :keyword group by r.rule_id")
    Page<CcrUserRule> findSupportRules(@Param("username") String username,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);

    //添加角色前，判断是否已经存在角色
    @Query(nativeQuery = true, value = "select r.* from ccr_user_rule as r where r.name=:name or r.content=:content")
    List<CcrUserRule> findByRuleName(@Param("name") String name,@Param("content") String content);

}
