package com.oj_timer.server.service;


import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class SubmissionServiceTest {

    @Autowired
    SubmissionService submissionService;
    @Autowired
    EntityManager em;

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
        dto.setLevel("silver 2®");
        dto.setTitle("problem title");

        // when
        SubmissionDto savedDto = submissionService.save(dto);
        em.flush();
        em.clear();

        submissionService.isExistsSubmissionByElementId(savedDto.getElementId(), savedDto.getUsername(), savedDto.getSite());

        // then
    }

}