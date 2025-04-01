package com.oj_timer.server.repository.mybatis;

import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.test.MyBatisSubmissionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecentSubmissionRepositoryTest {

    @Autowired
    RecentSubmissionRepository repository;

    @Test
    @DisplayName("MyBatis 테스트")
    void fetchRecentTest() {
        // given
        SubmissionSearchCondition condition = new SubmissionSearchCondition();
        condition.setEmail("email1@gmail.com");
        List<MyBatisSubmissionDto> dtos = repository.findRecentSubmissions(condition, PageRequest.of(0, 20));
        // when
        for (MyBatisSubmissionDto dto : dtos) {
            System.out.println(dto.toString());
        }
        // then
    }
}