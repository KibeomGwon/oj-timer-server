package com.oj_timer.server.repository.dummy;

import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import com.oj_timer.server.repository.dummy.dto.SubmissionTestDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class SubmissionDummyRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubmissionDummyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void bulkInsert(List<SubmissionTestDto> submissions) {
        String query = "INSERT INTO submission (member_id, problem_id, element_id, submission_time, username, language) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SubmissionTestDto submission = submissions.get(i);
                ps.setLong(1, submission.getMemberId());
                ps.setLong(2, submission.getProblemId());
                ps.setString(3, submission.getElementId());
                ps.setString(4, submission.getSubmissionTime());
                ps.setString(5, submission.getUsername());
                ps.setString(6, submission.getLanguage());
            }

            @Override
            public int getBatchSize() {
                return submissions.size();
            }
        });
    }

    public Long lastInsertId() {
        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
    }
}
