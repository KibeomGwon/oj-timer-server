package com.oj_timer.server.repository.dummy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionTestDto {
    private Long id;
    private Long problemId;
    private Long memberId;
    private String elementId;
    private String submissionTime;
    private String username;
    private String language;
}
