package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.KeyValueVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeyValueVORepository extends JpaRepository<KeyValueVO, String> {

    @Query(value = "SELECT user_group as key_string,count(*) as value_string FROM ccr_access_log GROUP BY user_group",nativeQuery = true)
    List<KeyValueVO> userGroupCount();

}
