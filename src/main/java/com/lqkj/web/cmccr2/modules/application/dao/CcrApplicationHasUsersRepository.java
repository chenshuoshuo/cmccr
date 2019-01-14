package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsers;
import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsersPK;
import com.lqkj.web.cmccr2.modules.application.domain.CcrVersionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CcrApplicationHasUsersRepository extends JpaRepository<CcrApplicationHasUsers,
        CcrApplicationHasUsersPK> {

    @Modifying
    @Query("delete from CcrApplicationHasUsers has where has.ccrPcApplicationAppId=:appId")
    void deleteByAppId(@Param("appId") Long appId);

    @Query("select has.hasUsersUserCode from CcrApplicationHasUsers has where has.ccrPcApplicationAppId=:appId")
    List<String> findUserIdsByAppId(@Param("appId") Long id);
}
