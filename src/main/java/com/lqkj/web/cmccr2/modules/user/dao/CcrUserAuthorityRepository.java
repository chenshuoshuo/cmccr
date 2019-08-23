package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CcrUserAuthorityRepository extends JpaRepository<CcrUserAuthority, Long> {

    @Query("select a from CcrUserAuthority a where a.type=:t and a.enabled=true")
    List<CcrUserAuthority> findByType(@Param("t") CcrUserAuthority.UserAuthorityType type);

    @Query("select a.name from CcrUserAuthority a where upper(a.content)=upper(:content) ")
    String findNameByContent(@Param("content") String content);

    @Modifying
    @Query("update CcrUserAuthority a set a.enabled=:enabled where a.parentId=:id")
    void updateChildState(@Param("id") Long id,
                          @Param("enabled") Boolean enabled);

    @Query(nativeQuery = true, value = "select a.* from ccr_user_authority a" +
            " inner join ccr_rule_to_authority ra on a.authority_id = ra.authority_id " +
            " inner join ccr_user_rule r on ra.rule_id = r.rule_id " +
            " inner join ccr_user_to_rule ur on r.rule_id = ur.rule_id " +
            " inner join ccr_user u on ur.user_id = u.user_id " +
            " where u.user_code=:userName and a.name like :keyword group by a.authority_id")
    Page<CcrUserAuthority> findSupportAuthority(@Param("userName") String userName,
                                                @Param("keyword") String keyword,
                                                Pageable pageable);

    @Query(value = "select * from ccr_user_authority where target_user_role && ARRAY[?2,'public'] \\:\\:varchar[] or specify_user_id && ARRAY[?1] \\:\\:varchar[] group by authority_id",nativeQuery = true)
    List<CcrUserAuthority> listQuery(String userId, String roles);
}
