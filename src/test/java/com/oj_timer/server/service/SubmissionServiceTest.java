package com.oj_timer.server.service;


import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubmissionServiceTest {

    @Autowired
    SubmissionService submissionService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("테스트 객체들 불러오기")
    @Order(1)
    void init() {
        // given
        submissionService.getRecentSubmissionsPaging("email20@gmail.com", new SubmissionSearchCondition(), PageRequest.of(0, 20));
        // when

        // then
    }

    @Test
    @DisplayName("메인 페이지 데이터 불러오기 시간 테스트")
    @Order(2)
    void fetchMainPageDatas() {
        // given
        Page<RecentSubmissionDto> dtos = submissionService
                .getRecentSubmissionsPaging("email12@gmail.com", new SubmissionSearchCondition(), PageRequest.of(0, 20));
        // when
        List<RecentSubmissionDto> content = dtos.getContent();
        // then
        assertThat(content.size()).isEqualTo(20);
        for (RecentSubmissionDto recentSubmissionDto : content) {
            System.out.println(recentSubmissionDto.toString());
        }
    }

    @Test
    @DisplayName("프록시가 적용되는지 확인하는 테스트")
    void isProxy() {
        System.out.println(submissionService.getClass()); // 프록시가 적용되어있다.
    }

}