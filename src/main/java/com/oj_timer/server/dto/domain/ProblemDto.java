package com.oj_timer.server.dto.domain;

import com.oj_timer.server.entity.Problem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemDto {
    private String problemTitleId;
    private String level;
    private String site;
    private String link;
    private String title;

    public Problem toEntity() {
        return Problem.create(problemTitleId, level, site, link, title);
    }

    public static ProblemDto toDto(Problem problem) {
        return ProblemDto.builder().
                problemTitleId(problem.getProblemTitleId())
                .level(problem.getLevel())
                .site(problem.getSite())
                .link(problem.getLink())
                .title(problem.getTitle())
                .build();
    }
}
