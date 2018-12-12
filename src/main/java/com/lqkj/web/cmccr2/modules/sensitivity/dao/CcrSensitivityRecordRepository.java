package com.lqkj.web.cmccr2.modules.sensitivity.dao;

import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CcrSensitivityRecordRepository extends JpaRepository<CcrSensitivityRecord, UUID> {
}
