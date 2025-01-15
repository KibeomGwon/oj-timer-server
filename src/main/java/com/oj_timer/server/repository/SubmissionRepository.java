package com.oj_timer.server.repository;

import com.oj_timer.server.dto.SubmissionDto;
import com.oj_timer.server.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("select s from Submission s join s.problem p on s.problem = p where s.elementId = ?1 and s.username = ?2 and p.site = ?3")
    Optional<Submission> findByElementIdAndUsernameAndSite(String elementId, String username, String site);

    @Query("select new com.oj_timer.server.dto.SubmissionDto(s.elementId, s.submissionTime, s.username, p.problemTitleId, p.level, p.site, p.link) from Submission s join s.problem p on s.problem = p where p.problemTitleId = ?1 and s.username = ?2 order by s.submissionTime desc")
    Page<SubmissionDto> findAllByProblemTitleIdAndUsername(String problemTitleId, String username, Pageable pageable);
}
