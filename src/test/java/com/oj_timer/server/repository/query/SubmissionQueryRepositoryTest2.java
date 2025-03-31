package com.oj_timer.server.repository.query;

import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class SubmissionQueryRepositoryTest2 {
    @Autowired
    SubmissionQueryRepository repository;

    @Test
    @DisplayName("메인페이지에 들어가면 가장 먼저 나오는 문제들의 최근 푼 시간")
    void fetchSubmissions() {
        // given
        SubmissionSearchCondition condition = new SubmissionSearchCondition();
        condition.setEmail("email1@gmail.com");

        Page<RecentSubmissionDto> pages = repository.findRecentSubmissionPage(condition, PageRequest.of(0, 20));
        // when
        System.out.println(pages.getTotalElements());
        System.out.println(pages.getContent().toString());

        // then
    }

    @Test
    @DisplayName("선택 객체")
    void fetchSelectObjects() {
        // given
        List<SubmissionQueryRepository.SelectObject> objects = repository.getSelectObjects("email1@gmail.com");
        // when
        System.out.println(objects.toString());
        // then
    }
}
