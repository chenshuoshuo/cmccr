package com.lqkj.web.cmccr2.modules.notification.dao;

import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Map;

@Repository
public interface CcrNotificationRepository extends JpaRepository<CcrNotification, Integer> {

    @Query(value = "SELECT * FROM ccr_notification WHERE 1=1 and " +
            "CASE WHEN ?1 is not null THEN title like concat('%',?1,'%') ELSE 1=1 END AND " +
            "CASE WHEN ?2='公开' THEN array_to_string(target_user_role, ',') = '游客' " +
            "WHEN ?2='指定用户' THEN specify_user_id is not null ELSE 1=1 END " +
            "ORDER BY post_time",nativeQuery = true)
    Page<CcrNotification> page(String title, String auth, Pageable pageable);

}
