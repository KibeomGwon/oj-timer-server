package com.oj_timer.server.repository;

import com.oj_timer.server.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("select s from Submission s join s.problem p on s.problem = p where s.elementId = ?1 and s.username = ?2 and p.site = ?3")
    Optional<Submission> findByElementIdAndUsernameAndSite(String elementId, String username, String site);
}
