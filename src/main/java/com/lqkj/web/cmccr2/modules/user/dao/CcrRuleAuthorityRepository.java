package com.lqkj.web.cmccr2.modules.user.dao;


import com.lqkj.web.cmccr2.modules.user.domain.CcrRuleToAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrRuleAuthorityRepository extends JpaRepository<CcrRuleToAuthorityEntity, Long> {
    @Modifying
    @Query(value="DELETE from ccr_rule_to_authority as crta where crta.rule_id=:ruleId", nativeQuery = true)
    void deleteByRuleId(@Param("ruleId") Long ruleId);
}
