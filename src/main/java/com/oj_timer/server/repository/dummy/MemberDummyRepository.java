package com.oj_timer.server.repository.dummy;

import com.oj_timer.server.entity.Member;
import com.oj_timer.server.entity.Problem;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDummyRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberDummyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void bulkInsert(List<Member> members) {
        String query = "INSERT INTO member(email, password) VALUES (?,?)";

        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Member member = members.get(i);
                ps.setString(1, member.getEmail());
                ps.setString(2, member.getPassword());
            }

            @Override
            public int getBatchSize() {
                return members.size();
            }
        });
    }

    public Long lastInsertId() {
        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
    }

    public Long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MEMBER", Long.class);
    }
}
