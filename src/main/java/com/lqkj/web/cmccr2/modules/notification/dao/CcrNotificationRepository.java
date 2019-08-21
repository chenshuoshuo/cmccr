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

@Repository
public interface CcrNotificationRepository extends JpaRepository<CcrNotification, Integer> {

}
