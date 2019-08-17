package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import com.lqkj.web.cmccr2.modules.application.domain.CcrRecommendedApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CcrRecommendedApplicationRepository extends JpaRepository<CcrRecommendedApplication, String> {

    @Query("SELECT ra from CcrRecommendedApplication ra where ra.startTime <=?1 and ra.endTime >=?1 order by ra.orderId")
    List<CcrRecommendedApplication> findAllByTime(Timestamp systemTime);
}
