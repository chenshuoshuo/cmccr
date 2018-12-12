package com.lqkj.web.cmccr2.modules.sensitivity.dao;

import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrSensitivityWordRepository extends JpaRepository<CcrSensitivityWord, Long> {
}
