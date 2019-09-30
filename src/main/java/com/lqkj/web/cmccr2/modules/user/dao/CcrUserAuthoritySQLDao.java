package com.lqkj.web.cmccr2.modules.user.dao;

import com.lqkj.web.cmccr2.modules.notification.dao.RepositoryUtil;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CcrUserAuthoritySQLDao extends RepositoryUtil<CcrUserAuthority> {
}

