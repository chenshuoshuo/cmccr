package com.lqkj.web.cmccr2.modules.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 批量更新用户
 */
@Repository
public class CcrUserBatchRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void bulkMergeUser(String sql){
        jdbcTemplate.execute(sql);
    }
}
