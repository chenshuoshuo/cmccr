package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrClickRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CcrClickRecordRepository extends JpaRepository<CcrClickRecord, UUID> {
}
