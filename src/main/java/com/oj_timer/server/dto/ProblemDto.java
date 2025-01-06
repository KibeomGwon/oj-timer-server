package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Problem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemDto {
    private String problemTitle;
    private String level;
    private String site;
    private String link;

    public Problem toEntity() {
        return Problem.create(problemTitle, level, site, link);
    }
}
