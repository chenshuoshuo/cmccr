package com.lqkj.web.cmccr2.modules.notification.dao;

import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotificationRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CcrNotificationReadRepository extends JpaRepository<CcrNotificationRead, Integer> {

}
