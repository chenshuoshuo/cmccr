package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CcrUserAuthorityRepository extends JpaRepository<CcrUserAuthority, Long> {

    @Query("select a from CcrUserAuthority a where a.type=:t and a.enabled=true")
    List<CcrUserAuthority> findByType(@Param("t") CcrUserAuthority.UserAuthorityType type);

    @Query("select a.name from CcrUserAuthority a where upper(a.content)=upper(:content) ")
    String findNameByContent(@Param("content") String content);
}
