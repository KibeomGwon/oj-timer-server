package com.oj_timer.server.service;

import com.oj_timer.server.dto.BaekjoonSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        BaekjoonSubmissionDto dto = new BaekjoonSubmissionDto();
        dto.setProblemId("problem title");
        dto.setElementId("solution-1122233");
        dto.setLanguage("java");
        dto.setSite("baekjoon");
        dto.setSubmissionTime(LocalDateTime.now());
        dto.setUsername("user1");
        dto.setLink("https://baekjoon.org");
        dto.setLevel("silver 2®");

        // when
        SubmissionDto savedDto = submissionService.save(dto);
        em.flush();
        em.clear();

        submissionService.isExistsSubmissionByElementId(savedDto.getElementId(), savedDto.getUsername(), savedDto.getSite());

        // then
    }

}