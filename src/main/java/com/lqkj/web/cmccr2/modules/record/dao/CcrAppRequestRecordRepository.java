package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CcrAppRequestRecordRepository extends JpaRepository<CcrAppRequestRecord, UUID> {

    @Query(nativeQuery = true, value = "select ap.name, t._count from " +
            "(select app_id, count(*) _count from ccr_app_request_record group by app_id) t, ccr_application ap " +
            "where t.app_id = ap.app_id order by t._count desc limit 5;")
    List<Object[]> topRequest();
}
