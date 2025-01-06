package com.oj_timer.server.repository;

import com.oj_timer.server.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findProblemByProblemTitle(String problemTitle);
}
