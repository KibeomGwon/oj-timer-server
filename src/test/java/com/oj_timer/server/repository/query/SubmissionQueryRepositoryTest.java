package com.oj_timer.server.repository.query;

import com.oj_timer.server.config.AppConfig;
import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.domain.SubmissionDto;
import com.oj_timer.server.entity.Member;
import com.oj_timer.server.service.SubmissionService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Import(AppConfig.class)
class SubmissionQueryRepositoryTest {

    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionQueryRepository repository;
    @Autowired
    EntityManager em;

//    @Test
    public void getTest() throws Exception {
        // given
        SubmissionSearchCondition cond = new SubmissionSearchCondition();
        cond.setSite("백준");

        // when
        Page<RecentSubmissionDto> result = repository.findRecentSubmissionPage(cond, PageRequest.of(0, 10));

        // then
        for (RecentSubmissionDto dto : result.getContent()) {
            System.out.println(dto.toString());
        }

        Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
    }


    void init() {
        InputSubmissionDto dto = new InputSubmissionDto();
        dto.setProblemId("1234");
        dto.setElementId("solution-1122233");
        dto.setLanguage("java");
        dto.setSite("baekjoon");
        dto.setSubmissionTime(LocalDateTime.now());
        dto.setUsername("user1");
        dto.setLink("https://baekjoon.org");
        dto.setLevel("silver 2");
        dto.setTitle("problem title");

        InputSubmissionDto dto2 = new InputSubmissionDto();
        dto2.setProblemId("2222");
        dto2.setElementId("solution-2222222");
        dto2.setLanguage("java");
        dto2.setSite("baekjoon");
        dto2.setSubmissionTime(LocalDateTime.now());
        dto2.setUsername("user1");
        dto2.setLink("https://baekjoon.org");
        dto2.setLevel("silver 1");
        dto2.setTitle("problem title");


        InputSubmissionDto dto3 = new InputSubmissionDto();
        dto3.setProblemId("33333");
        dto3.setElementId("solution-33333");
        dto3.setLanguage("C");
        dto3.setSite("baekjoon");
        dto3.setSubmissionTime(LocalDateTime.now());
        dto3.setUsername("user1");
        dto3.setLink("https://baekjoon.org");
        dto3.setLevel("silver 4");
        dto3.setTitle("problem title");

        InputSubmissionDto dto4 = new InputSubmissionDto();
        dto4.setProblemId("44444");
        dto4.setElementId("solution-444444");
        dto4.setLanguage("java");
        dto4.setSite("programmers");
        dto4.setSubmissionTime(LocalDateTime.now());
        dto4.setUsername("user1");
        dto4.setLink("https://baekjoon.org");
        dto4.setLevel("3");


        InputSubmissionDto dto5 = new InputSubmissionDto();
        dto5.setProblemId("555555");
        dto5.setElementId("solution-555555");
        dto5.setLanguage("MySql");
        dto5.setSite("programmers");
        dto5.setSubmissionTime(LocalDateTime.now());
        dto5.setUsername("user1");
        dto5.setLink("https://baekjoon.org");
        dto5.setLevel("4");

        Member member = Member.create("email", "password", "01011111111");
        em.persist(member);

        // when
        SubmissionDto savedDto = submissionService.save(member.getEmail(), dto);
        SubmissionDto savedDto1 = submissionService.save(member.getEmail(), dto2);
        SubmissionDto savedDto2 = submissionService.save(member.getEmail(), dto3);
        submissionService.save(member.getEmail(), dto4);
        submissionService.save(member.getEmail(), dto5);
        em.flush();
        em.clear();
    }

//    @Test
    public void fetchSelectObjects() throws Exception {
        // given
        init();
        // when

        List<SubmissionQueryRepository.SelectObject> email = repository.getSelectObjects("email");

        for (SubmissionQueryRepository.SelectObject selectObject : email) {
            System.out.println(selectObject.toString());
        }

        SubmissionSearchCondition con = new SubmissionSearchCondition();

        con.setEmail("email");

        Page<RecentSubmissionDto> page = repository.findRecentSubmissionPage(con, PageRequest.of(0, 20));
        for (RecentSubmissionDto recentSubmissionDto : page.getContent()) {
            System.out.println(recentSubmissionDto.toString());
        }

        // then
        Assertions.assertThat(page.getContent().size()).isEqualTo(5);
    }

}