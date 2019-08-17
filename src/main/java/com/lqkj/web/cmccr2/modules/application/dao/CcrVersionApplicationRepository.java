package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrVersionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CcrVersionApplicationRepository extends JpaRepository<CcrVersionApplication, Long> {

    @Query(nativeQuery = true, value = "select app.name,app.download_count from ccr_version_application app order by " +
            "app.download_count desc limit 5")
    List<Object[]> downloadRecord();
}
