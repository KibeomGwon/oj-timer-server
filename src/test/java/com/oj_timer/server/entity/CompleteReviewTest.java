package com.oj_timer.server.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CompleteReviewTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        Member member = Member.create("email", "password", "phone");
        Problem problem1 = Problem.create("title1", "Gold II", "baekjoon", "link", "title1");
        Problem problem2 = Problem.create("title2", "Gold II", "baekjoon", "link", "title2");
        Problem problem3 = Problem.create("title3", "Gold II", "baekjoon", "link", "title3");
        em.persist(member);
        em.persist(problem1);
        em.persist(problem2);
        em.persist(problem3);

        em.flush();
        em.clear();
    }

    @Test
    public void save() throws Exception {
        // given
        Member member = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", "email")
                .getSingleResult();
        Problem findProblem = em.createQuery("select p from Problem p where p.problemTitleId =: titleId", Problem.class)
                .setParameter("titleId", "title1")
                .getSingleResult();
        // when
        member.completeReview(findProblem);
        em.flush();
        em.clear();

        Member findMember = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", "email")
                .getSingleResult();

        CompleteReview completeReview = em.find(CompleteReview.class, findMember.getCompleteReviews().get(0).getId());
        // then
        assertThat(completeReview.getProblem().getId()).isEqualTo(findProblem.getId());
        for (CompleteReview review : findMember.getCompleteReviews()) {
            System.out.println(review.getId());
        }

    }

    @Test
    public void remove() {
        Member member = em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", "email")
                .getSingleResult();
        Problem problem1 = em.createQuery("select p from Problem p where p.problemTitleId =: titleId", Problem.class)
                .setParameter("titleId", "title1")
                .getSingleResult();
        Problem problem2 = em.createQuery("select p from Problem p where p.problemTitleId =: titleId", Problem.class)
                .setParameter("titleId", "title2")
                .getSingleResult();
        Problem problem3 = em.createQuery("select p from Problem p where p.problemTitleId =: titleId", Problem.class)
                .setParameter("titleId", "title3")
                .getSingleResult();
        // when
        member.completeReview(problem1);
        member.completeReview(problem2);
        member.completeReview(problem3);

        em.flush();
        em.clear();

        Member findMember = em.createQuery("select m from Member m join fetch m.completeReviews c where m.email =: email", Member.class)
                .setParameter("email", "email")
                .getSingleResult();

        Long id = findMember.getCompleteReviews().get(0).getId();
        findMember.needToSolveAgain(findMember.getCompleteReviews().get(0).getId());

        em.flush();
        em.clear();

        Problem findProblem1 = em.find(Problem.class, problem1.getId());

        assertThat(findProblem1.getId()).isEqualTo(problem1.getId());
        try {
            CompleteReview completeReview = em.find(CompleteReview.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e).cause();
        }
    }
}