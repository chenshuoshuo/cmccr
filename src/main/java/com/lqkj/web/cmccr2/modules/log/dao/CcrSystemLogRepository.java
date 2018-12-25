package com.lqkj.web.cmccr2.modules.log.dao;

import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface CcrSystemLogRepository extends JpaRepository<CcrSystemLog, UUID> {

    @Query("select log from CcrSystemLog log where log.createTime>:startTime and log.createTime<:endTime")
    Page<CcrSystemLog> pageByTime(@Param("startTime")Timestamp startTime,
                                  @Param("endTime") Timestamp endTime,
                                  Pageable pageable);
}
