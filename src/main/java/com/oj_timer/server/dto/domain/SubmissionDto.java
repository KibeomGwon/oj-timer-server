package com.oj_timer.server.dto.domain;

import com.oj_timer.server.entity.Submission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto {
    private String elementId;
    private LocalDateTime submissionTimeId;
    private String username;
    private String problemTitle;
    private String level;
    private String site;
    private String link;

    public static SubmissionDto toDto(Submission submission) {
        return SubmissionDto.builder()
                .elementId(submission.getElementId())
                .submissionTimeId(submission.getSubmissionTime())
                .username(submission.getUsername())
                .problemTitle(submission.getProblem().getProblemTitleId())
                .site(submission.getProblem().getSite())
                .level(submission.getProblem().getLevel())
                .link(submission.getProblem().getLink())
                .build();
    }
}
