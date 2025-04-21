package com.oj_timer.server.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.oj_timer.server.entity.QProblem.*;
import static com.oj_timer.server.entity.QSubmission.*;

@Repository
@Slf4j
@CacheConfig(cacheNames = "selections")
public class SubmissionQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory factory;

    @Autowired
    public SubmissionQueryRepository(EntityManager em) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
    }

    @Cacheable(key = "#email", unless = "#result == null")
    public SelectObjects getSelectObjects(String email) {
        log.info("SELECTIONS FOR {}", email);
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
        return new SelectObjects(fetch);
    }

    @Data
    public static class SelectObjects {
        private List<SelectObject> selectObjects = new ArrayList<>();

        public SelectObjects() {
        }

        public SelectObjects(List<SelectObject> selectObjects) {
            this.selectObjects = selectObjects;
        }
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
}
