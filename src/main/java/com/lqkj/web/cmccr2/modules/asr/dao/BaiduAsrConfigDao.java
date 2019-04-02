package com.lqkj.web.cmccr2.modules.asr.dao;

import com.lqkj.web.cmccr2.modules.asr.domain.BaiduAsrConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiduAsrConfigDao extends JpaRepository<BaiduAsrConfig, Integer> {
}
