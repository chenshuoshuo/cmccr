package com.lqkj.web.cmccr2.modules.log.dao;

import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CcrSystemLogRepository extends JpaRepository<CcrSystemLog, UUID> {
}
