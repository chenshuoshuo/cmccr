package com.lqkj.web.cmccr2.modules.application.dao;

import com.lqkj.web.cmccr2.modules.application.domain.CcrApplicationHasUsers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CcrPcApplicationBatchDao {
    private JdbcTemplate jdbcTemplate;

    public CcrPcApplicationBatchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertHasUsers(List<CcrApplicationHasUsers> hasUsers) {
        if (hasUsers.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder();

        sql.append("insert into ccr_application_has_users values ");

        for (int i = 0; i < hasUsers.size(); i++) {
            CcrApplicationHasUsers hasUser = hasUsers.get(i);

            sql.append("(" + hasUser.getCcrPcApplicationAppId() + ",")
                    .append("'").append(hasUser.getHasUsersUserCode()).append("'")
                    .append(")");

            if (i!=hasUsers.size() - 1) {
                sql.append(",\n");
            }
        }

        jdbcTemplate.execute(sql.toString());
    }
}
