package com.oj_timer.server.repository;

import com.oj_timer.server.AppConfig;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest   == inMemory 방식 -> h2 db가 필요
@SpringBootTest
@Transactional
@Import(AppConfig.class)
class SubmissionRepositoryTest {
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    ProblemRepository problemRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void saveTest() throws Exception {
        // given
        Problem problem = Problem.create("baekjoon_1004", "Gold V", "baekjoon", "link", "title");
        Submission submission1 = Submission.create("111111", LocalDateTime.of(2025, 1, 5, 21, 9), "user1", problem);
        Submission submission2 = Submission.create("222222", LocalDateTime.of(2025, 1, 5, 21, 10), "user1", problem);
        Submission submission3 = Submission.create("333333", LocalDateTime.of(2025, 1, 20, 21, 11), "user1", problem);

        problemRepository.save(problem);
        submissionRepository.save(submission1);
        submissionRepository.save(submission2);
        submissionRepository.save(submission3);

        em.flush();
        em.clear();
        // when

        Submission findSubmission = em
                .createQuery("select s from Submission s join Problem p on s.problem = p where p.site =: site order by s.submissionTime desc limit 1", Submission.class)
                .setParameter("site", "baekjoon")
                .getSingleResult();
        // then
        assertThat(findSubmission.getElementId()).isEqualTo("333333");
        assertThat(findSubmission.getProblem().getProblemTitleId()).isEqualTo("baekjoon_1004");
    }
}