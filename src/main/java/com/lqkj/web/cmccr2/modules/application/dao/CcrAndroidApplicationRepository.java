package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrAndroidApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrAndroidApplicationRepository extends JpaRepository<CcrAndroidApplication, Long> {
}
