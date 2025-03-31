package com.oj_timer.server.repository;

import com.oj_timer.server.config.AppConfig;
import com.oj_timer.server.dto.domain.SubmissionDto;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SubmissionRepositoryTest2 {
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    ProblemRepository problemRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("제출 찾기")
    void fetchSubmission() {
        // given
        Submission submission = submissionRepository.findByElementIdAndUsernameAndSite("90203202503895712", "user86212", "프로그래머스", "email1@gmail.com").get();

        // when
        Assertions.assertThat(submission.getProblem().getTitle()).isEqualTo("문제3989");
        // then
    }
}