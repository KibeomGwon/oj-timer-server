package com.oj_timer.server.dummy.entity;

import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.repository.dummy.ProblemDummyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class CreateProblemDummy {

    @Autowired
    ProblemDummyRepository repository;
    @Test
    @DisplayName("테스트 데이터 insert")
    void insertTestData() {
        // given
        List<Problem> problems = getProblems();
        // when
        Long lastId = repository.lastInsertId();

        IntStream.range(0, problems.size()).forEach(i -> {
            long id = lastId + (long) i;
            problems.get(i).setId(id);
            problems.get(i).setProblemTitleId(String.valueOf(id));
            System.out.println();
        });
        repository.bulkInsert(problems);
        // then
    }

    List<Problem> getProblems() {
        List<Problem> problems = new ArrayList<>();

        Problem problem;

        for (int i = 0; i < 10000; i++) {
            problem = Problem.create(String.valueOf(i), String.valueOf(i % 6), i % 2 == 0 ? "백준" : "프로그래머스", "", "문제" + i);
            problems.add(problem);
        }

        return problems;
    }
}
