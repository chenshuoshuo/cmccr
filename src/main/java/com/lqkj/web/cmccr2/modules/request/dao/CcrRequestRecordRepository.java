package com.lqkj.web.cmccr2.modules.request.dao;

import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface CcrRequestRecordRepository extends JpaRepository<CcrRequestRecord, UUID> {

//    @Query("select ((r.createTime - :startTime) / :frequency) as p,count(r) from CcrRequestRecord r " +
//            "where r.createTime>:startTime and r.createTime<:endTime and r.successed=:successed " +
//            "group by p")
//    Object[] selectByTimeRegion(@Param("startTime") Timestamp startTime,
//                                              @Param("endTime") Timestamp endTime,
//                                              @Param("frequency") long frequency,
//                                              @Param("successed") boolean successed);
}
