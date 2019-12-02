package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrRequestRecord;
import org.springframework.data.domain.Page;
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

/*
    @Query(nativeQuery = true, value = "select case when 'create_month' = :frequency then create_month" +
            " when 'create_date' = :frequency then create_date else create_hour end as t,count(r) from ccr_request_record r " +
            "where r.create_time>:startTime and r.create_time<:endTime and r.successed=:successed " +
            "group by t order by t")
    List<Object[]> dataRecord(@Param("startTime") Timestamp startTime,
                              @Param("endTime") Timestamp endTime,
                              @Param("frequency") String frequency,
                              @Param("successed") Boolean successed);
*/

    @Query(nativeQuery = true, value = "select (string_to_array(r.url,'/'))[4] ar,count(r) " +
            "from ccr_request_record r where r.create_time>:startTime and r.create_time<:endTime group by ar",
            countQuery = "select count(*) from (select (string_to_array(r.url, '/'))[4] ar\n" +
                    "                      from ccr_request_record r\n" +
                    "                      where r.create_time>:startTime\n" +
                    "                        and r.create_time<:endTime\n" +
                    "                      group by ar) t")
    Page<Object[]> urlRecord(@Param("startTime") Timestamp startTime,
                             @Param("endTime") Timestamp endTime,
                             Pageable pageable);

    @Query(nativeQuery = true, value = "select to_char(r.create_time,'YYYY-MM-DD HH24') as t,count(r) from ccr_request_record r " +
            "where r.create_time>:startTime and r.create_time<:endTime and (string_to_array(r.url,'/'))[4]=:name" +
            " group by t order by t")
    List<Object[]> urlRecordDetail(@Param("startTime") Timestamp startTime,
                                   @Param("endTime") Timestamp endTime,
                                   @Param("name") String name);

    @Query(nativeQuery = true, value = "select ip,count(r) " +
            "from ccr_request_record r where r.create_time>:startTime and r.create_time<:endTime group by ip;")
    List<Object[]> locationRecord(@Param("startTime") Timestamp startTime,
                                  @Param("endTime") Timestamp endTime);

    @Query("select r from CcrRequestRecord r where r.createTime>:startTime and r.createTime<:endTime " +
            "and r.successed=false")
    Page<CcrRequestRecord> errorRecord(@Param("startTime") Timestamp startTime,
                                       @Param("endTime") Timestamp endTime,
                                       Pageable pageable);

    @Query(nativeQuery = true, value = "select count(t) from (select r.ip from ccr_request_record r group by r.ip) t")
    Integer ipRecord();

    @Query(nativeQuery = true, value = "select t.user_group, count(*) _count" +
            " from ccr_request_record t" +
            " group by t.user_group" +
            " order by t.user_group")
    List<Object[]> userGroupStatistic();
}
