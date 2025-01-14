package com.oj_timer.server.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory factory;

    @Autowired
    public ProblemQueryRepository(EntityManager em) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
    }

}
