package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Problem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionDto {
    private String elementId;
    private LocalDateTime submissionTime;
    private String username;
    private String problemTitle;
    private String level;
    private String site;
    private String link;
}
