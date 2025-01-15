package com.oj_timer.server.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class ProblemAndSubmissionsDto {
    private ProblemDto problemDto;
    private Page<SubmissionDto> submissionDtos;

    public static ProblemAndSubmissionsDto create(ProblemDto problemDto, Page<SubmissionDto> submissionDtos) {
        return ProblemAndSubmissionsDto.builder()
                .problemDto(problemDto)
                .submissionDtos(submissionDtos).
                build();
    }
}
