package com.oj_timer.server.dto;

import com.oj_timer.server.dto.domain.ProblemDto;
import com.oj_timer.server.dto.domain.SubmissionDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

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
