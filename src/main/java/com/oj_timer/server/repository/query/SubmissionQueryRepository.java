package com.oj_timer.server.repository.query;

import com.oj_timer.server.dto.QRecentSubmissionDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.entity.QProblem;
import com.oj_timer.server.entity.QSubmission;
import com.oj_timer.server.entity.Submission;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.oj_timer.server.entity.QProblem.*;
import static com.oj_timer.server.entity.QSubmission.*;

@Repository
public class SubmissionQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory factory;

    @Autowired
    public SubmissionQueryRepository(EntityManager em) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
    }

    public Page<RecentSubmissionDto> findRecentSubmissionPage(SubmissionSearchCondition condition, Pageable pageable) {

        JPQLQuery<LocalDateTime> where = JPAExpressions.
                select(submission.submissionTime.max())
                .from(submission)
                .where(submission.problem.id.eq(problem.id));

        List<RecentSubmissionDto> fetched = factory
                .select(getSubmissionDto())
                .from(submission)
                .join(submission.problem, problem)
                .where(
                        problem.site.contains(condition.getSite()),
                        submission.username.eq(condition.getUsername()),
                        submission.submissionTime.eq(where)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = fetched != null ? fetched.stream().count() : 0;

        return new PageImpl<>(fetched, pageable, count);
    }

    private static QRecentSubmissionDto getSubmissionDto() {
        return new QRecentSubmissionDto(
                submission.username,
                submission.elementId,
                problem.title,
                submission.problem.problemTitleId,
                problem.site,
                submission.submissionTime,
                problem.level,
                problem.link);
    }

}
