package com.oj_timer.server.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBatisSubmissionDto {
    private String email;
    private LocalDateTime submissionTime;
    private String problemTitle;
    private String level;
    private String site;
    private String link;
    private String language;
}
