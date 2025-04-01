package com.oj_timer.server.repository.query;

import com.oj_timer.server.dto.QRecentSubmissionDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.entity.QSubmission;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.oj_timer.server.entity.QMember.member;
import static com.oj_timer.server.entity.QProblem.*;
import static com.oj_timer.server.entity.QSubmission.*;
import static io.micrometer.common.util.StringUtils.*;

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
                        siteEq(condition.getSite()),
                        submission.member.email.eq(condition.getEmail()),
                        submission.submissionTime.eq(where),
                        languageEq(condition.getLanguage()),
                        titleLike(condition.getTitle()),
                        levelEq(condition.getLevel())
                )
                .orderBy(submission.submissionTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = factory
                .select(submission.count())
                .from(submission)
                .join(submission.problem, problem)
                .where(
                        siteEq(condition.getSite()),
                        submission.member.email.eq(condition.getEmail()),
                        submission.submissionTime.eq(where),
                        languageEq(condition.getLanguage()),
                        titleLike(condition.getTitle()),
                        levelEq(condition.getLevel())
                )
                .orderBy(submission.submissionTime.desc())
                .fetchOne();

        return new PageImpl<>(fetched, pageable, count);
    }

    public List<SelectObject> getSelectObjects(String email) {
        List<SelectObject> fetch = factory
                .select(Projections.fields(SelectObject.class,
                        problem.site.as("site"),
                        submission.language.as("language"),
                        problem.level.as("level")
                ))
                .from(submission)
                .join(problem)
                .on(submission.problem.eq(problem))
                .where(submission.member.email.eq(email))
                .groupBy(problem.site, submission.language, problem.level)
                .fetch();
        return fetch;
    }


    @Data
    public static class SelectObject {
        private String site;
        private String language;
        private String level;

        public SelectObject() {
        }

        public SelectObject(String site, String language, String level) {
            this.site = site;
            this.language = language;
            this.level = level;
        }

        @Override
        public String toString() {
            return "SelectObject{" +
                    "site='" + site + '\'' +
                    ", language='" + language + '\'' +
                    ", level='" + level + '\'' +
                    '}';
        }
    }


    private BooleanExpression emailEq(String email) {
        return isEmpty(email) ? null : member.email.eq(email);
    }

    private BooleanExpression languageEq(String language) {
        return isEmpty(language) ? null : submission.language.eq(language);
    }

    private BooleanExpression titleLike(String title) {
        return isEmpty(title) ? null : problem.title.contains(title);
    }

    private BooleanExpression levelEq(String level) {
        return isEmpty(level) ? null : problem.level.eq(level);
    }

    private BooleanExpression siteEq(String site) {
        return isEmpty(site) ? null : problem.site.contains(site);
    }

    private QRecentSubmissionDto getSubmissionDto() {
        return new QRecentSubmissionDto(
                submission.member.email,
                submission.username,
                submission.elementId,
                problem.title,
                submission.problem.problemTitleId,
                problem.site,
                submission.submissionTime,
                problem.level,
                problem.link,
                submission.language
        );
    }

}
