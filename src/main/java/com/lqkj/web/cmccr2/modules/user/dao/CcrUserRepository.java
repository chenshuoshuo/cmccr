package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrUserRepository extends JpaRepository<CcrUser, Long> {

    @Query("select u from CcrUser u where u.userCode=:name")
    CcrUser findByUserName(@Param("name") String name);
}
