package com.oj_timer.server.service;


import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.domain.SubmissionDto;
import com.oj_timer.server.entity.Member;
import com.oj_timer.server.entity.Submission;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
        dto3.setLanguage("java");
        dto3.setSite("baekjoon");
        dto3.setSubmissionTime(LocalDateTime.now());
        dto3.setUsername("user1");
        dto3.setLink("https://baekjoon.org");
        dto3.setLevel("silver 4");
        dto3.setTitle("problem title");

        Member member = Member.create("email", "password", "01011111111");
        em.persist(member);

        // when
        SubmissionDto savedDto = submissionService.save(member.getEmail(), dto);
        SubmissionDto savedDto1 = submissionService.save(member.getEmail(), dto2);
        SubmissionDto savedDto2 = submissionService.save(member.getEmail(), dto3);
        em.flush();
        em.clear();
    }


    @Test
    public void save() throws Exception {
        // given
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

        Member member = Member.create("email", "password", "01011111111");
        em.persist(member);

        // when
        SubmissionDto savedDto = submissionService.save(member.getEmail(), dto);
        em.flush();
        em.clear();

        submissionService.isExistsSubmissionByElementId(member.getEmail(), savedDto.getElementId(), savedDto.getUsername(), savedDto.getSite());
        Member findMember = em.find(Member.class, member.getId());

        // then
        assertThat(findMember.getSubmissions().size()).isEqualTo(1);
        assertThat(findMember.getSubmissions()).extracting("elementId").containsExactly("solution-1122233");
    }

    @Test
    public void findByCondition() throws Exception {
        // given
        init();
        // when
        Page<RecentSubmissionDto> page = submissionService
                .getRecentSubmissionsPaging("email", new SubmissionSearchCondition(), PageRequest.of(0, 20));
        Member findMember = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", "email")
                .getSingleResult();
        List<Submission> submissions = em.createQuery("select s from Submission s", Submission.class).getResultList();

        // then
        assertThat(findMember.getSubmissions().size()).isEqualTo(3);
        assertThat(submissions.size()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getContent()).extracting("email").containsExactly("email", "email", "email");
    }

}