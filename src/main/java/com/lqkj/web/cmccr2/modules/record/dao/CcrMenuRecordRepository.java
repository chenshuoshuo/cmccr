package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CcrMenuRecordRepository extends JpaRepository<CcrMenuClickRecord, UUID> {

    @Query("select r.name,count(r) from CcrMenuClickRecord r group by r.name")
    List<Object[]> record();
}