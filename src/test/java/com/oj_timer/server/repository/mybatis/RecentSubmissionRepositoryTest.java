package com.oj_timer.server.repository.mybatis;

import com.oj_timer.server.controller.web.paging.PageUtil;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.test.MyBatisSubmissionDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Pageable pageable = PageRequest.of(319, 20);
        Page<RecentSubmissionDto> result = repository.findRecentSubmissions(condition, pageable);

        // when
        for (RecentSubmissionDto dto : result.getContent()) {
            System.out.println(dto.toString());
        }
        System.out.println(result.getTotalPages() + " " + result.getTotalElements());
        System.out.println(result.getNumber());
        // then
        PageUtil pageUtil = new PageUtil(result);
        Assertions.assertThat(result.getContent().size()).isEqualTo(20);
    }
}