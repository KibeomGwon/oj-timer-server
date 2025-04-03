package com.oj_timer.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentSubmissionDto {
    private Long problemId;
    private String email;
    private LocalDateTime recentSubmissionTime;
    private String title;
    private String level;
    private String site;
    private String link;
    private String language;
    private String username;
}
