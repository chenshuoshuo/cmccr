package com.lqkj.web.cmccr2.modules.notification.dao;

import com.lqkj.web.cmccr2.modules.notification.domain.CcrNotification;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CcrNotificationSQLDaoImpl extends RepositoryUtilImpl<Map<String,Object>> implements CcrNotificationSQLDao {
}
