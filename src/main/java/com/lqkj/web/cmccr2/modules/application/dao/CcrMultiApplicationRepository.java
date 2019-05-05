package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrMultiApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrMultiApplicationRepository extends JpaRepository<CcrMultiApplication,Long> {

    @Query("select app from CcrMultiApplication app where app.webURL=:webURL")
    CcrMultiApplication findByWebURL(@Param("webURL") String webURL);
}
