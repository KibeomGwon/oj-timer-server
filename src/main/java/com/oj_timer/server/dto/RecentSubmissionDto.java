package com.oj_timer.server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecentSubmissionDto {
    private String email;
    private String username;
    private String elementId;
    private String title;
    private String problemId; // problem_title_id
    private String site;
    private LocalDateTime recentSubmissionTime;
    private String level;
    private String link;

    @QueryProjection
    public RecentSubmissionDto(String email, String username, String elementId, String title, String problemId, String site, LocalDateTime recentSubmissionTime, String level, String link) {
        this.email = email;
        this.username = username;
        this.elementId = elementId;
        this.title = title;
        this.problemId = problemId;
        this.site = site;
        this.recentSubmissionTime = recentSubmissionTime;
        this.level = level;
        this.link = link;
    }
}
