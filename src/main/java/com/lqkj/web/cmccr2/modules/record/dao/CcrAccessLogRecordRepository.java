package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface CcrAccessLogRecordRepository extends JpaRepository<CcrAccessLogRecord, Integer> {

    @Query(value = "SELECT COUNT(*) FROM ccr_access_log WHERE log_time >= to_timestamp(?1,'yyyy-mm-dd hh24:mi:ss') AND log_time <= to_timestamp(?2,'yyyy-mm-dd hh24:mi:ss')",nativeQuery = true)
    int useCount(String startDate,String endDate);

    @Query("SELECT COUNT(ar.logId) FROM CcrAccessLogRecord ar")
    int useCountAll();

    @Query(value = "SELECT count(*) from (SELECT COUNT(DISTINCT ip_address) FROM ccr_access_log GROUP BY ip_address) ip",nativeQuery = true)
    Integer countByIpAddress();

    @Modifying
    @Query(value = "delete from ccr_access_log ar where ar.log_time >= to_timestamp(?1,'yyyy-mm-dd hh24:mi:ss') and log_time <= to_timestamp(?2,'yyyy-mm-dd hh24:mi:ss')",nativeQuery = true)
    void deleteAllByLogTime(String startDate,String endDate);

    @Query(value = "select * from ccr_access_log ar where ar.log_time >= to_timestamp(?1,'yyyy-mm-dd hh24:mi:ss') and log_time <= to_timestamp(?2,'yyyy-mm-dd hh24:mi:ss')",nativeQuery = true)
    List<CcrAccessLogRecord> listQuery(String startDate,String endDate);

    @Query(value = "select ip_address,count(a) from ccr_access_log a where a.log_time>:startTime and a.log_time<:endTime group by ip_address",nativeQuery = true)
    List<Object[]> locationRecord( Timestamp startTime,
                                   Timestamp endTime);


    @Query(nativeQuery = true, value = "select case when 'create_month' = :frequency then create_month" +
            " when 'create_date' = :frequency then create_date else create_hour end as t,count(r) from ccr_access_log r " +
            " where r.log_time>:startTime and r.log_time<:endTime" +
            " group by t order by t")
    List<Object[]> dataRecord(@Param("startTime") Timestamp startTime,
                              @Param("endTime") Timestamp endTime,
                              @Param("frequency") String frequency);
}
