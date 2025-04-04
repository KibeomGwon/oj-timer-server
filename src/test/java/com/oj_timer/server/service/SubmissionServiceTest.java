package com.oj_timer.server.service;


import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SubmissionServiceTest {

    @Autowired
    SubmissionService submissionService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("메인 페이지 데이터 불러오기 시간 테스트")
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

}