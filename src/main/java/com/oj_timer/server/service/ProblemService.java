package com.oj_timer.server.service;

import com.oj_timer.server.dto.ProblemDto;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemDto save(Problem problem) {
        Problem savedProblem = problemRepository.save(problem);

        return ProblemDto.builder()
                .problemTitleId(savedProblem.getProblemTitleId())
                .level(savedProblem.getLevel())
                .site(savedProblem.getSite())
                .link(savedProblem.getLink())
                .build();
    }
}
