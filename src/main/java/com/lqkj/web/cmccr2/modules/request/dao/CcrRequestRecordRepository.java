package com.lqkj.web.cmccr2.modules.request.dao;

import com.google.common.collect.Lists;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface CcrRequestRecordRepository extends JpaRepository<CcrRequestRecord, UUID> {

    @Query(nativeQuery = true, value = "select to_char(r.create_time,:frequency) as t,count(r) from ccr_request_record r " +
            "where r.create_time>:startTime and r.create_time<:endTime and r.successed=:successed " +
            "group by t order by t")
    List<Object[]> dataRecord(@Param("startTime") Timestamp startTime,
                              @Param("endTime") Timestamp endTime,
                              @Param("frequency") String frequency,
                              @Param("successed") Boolean successed);

    @Query(nativeQuery = true, value = "select (string_to_array(r.url,'/'))[4] ar,count(r) " +
            "from ccr_request_record r where r.create_time>:startTime and r.create_time<:endTime group by ar;")
    List<Object[]> urlRecord(@Param("startTime") Timestamp startTime,
                             @Param("endTime") Timestamp endTime);

    @Query(nativeQuery = true, value = "select ip,count(r) " +
            "from ccr_request_record r where r.create_time>:startTime and r.create_time<:endTime group by ip;")
    List<Object[]> locationRecord(@Param("startTime") Timestamp startTime,
                                  @Param("endTime") Timestamp endTime);

    @Query("select r from CcrRequestRecord r where r.createTime>:startTime and r.createTime<:endTime " +
            "and r.successed=false")
    Page<CcrRequestRecord> errorRecord(@Param("startTime") Timestamp startTime,
                                       @Param("endTime") Timestamp endTime,
                                       Pageable pageable);
}
