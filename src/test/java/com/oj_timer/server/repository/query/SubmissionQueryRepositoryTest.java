package com.oj_timer.server.repository.query;

import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SubmissionQueryRepositoryTest {

    @Autowired
    SubmissionQueryRepository repository;
    @Autowired
    EntityManager em;

//    @Test
    public void getTest() throws Exception {
        // given
        SubmissionSearchCondition cond = new SubmissionSearchCondition();
        cond.setSite("baekjoon");
        cond.setUsername("rrq0211");

        // when
        Page<RecentSubmissionDto> result = repository.findRecentSubmissionPage(cond, PageRequest.of(0, 10));

        // then
        for (RecentSubmissionDto dto : result.getContent()) {
            System.out.println(dto.toString());
        }

        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
    }

}