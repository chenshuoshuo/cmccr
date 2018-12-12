package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrVersionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrVersionApplicationRepository extends JpaRepository<CcrVersionApplication,Long> {
}
