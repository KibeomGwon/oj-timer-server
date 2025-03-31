package com.oj_timer.server.repository.dummy;

import com.oj_timer.server.entity.Problem;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProblemDummyRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProblemDummyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void bulkInsert(List<Problem> problems) {
        String query = "INSERT INTO `problem`(level, problem_title_id, site, title) VALUES (?,?,?,?)";

        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Problem problem = problems.get(i);
                ps.setString(1, problem.getLevel());
                ps.setString(2, problem.getProblemTitleId());
                ps.setString(3, problem.getSite());
                ps.setString(4, problem.getTitle());
            }

            @Override
            public int getBatchSize() {
                return problems.size();
            }
        });
    }

    public Long lastInsertId() {
        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
    }

    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) from problem", Long.class);
    }
}