package com.oj_timer.server.service;

import com.oj_timer.server.exception_handler.exceptions.BadRequestException;
import com.oj_timer.server.dto.BaekjoonSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.repository.ProblemRepository;
import com.oj_timer.server.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    public SubmissionDto save(BaekjoonSubmissionDto dto) throws BadRequestException {
        Problem problem = dto.toProblem();

        if (!isExistsProblem(problem.getProblemTitle())) {
            problemRepository.save(problem);
        } else {
            problem = problemRepository.findProblemByProblemTitle(problem.getProblemTitle())
                    .orElseThrow(() -> new BadRequestException("문제 정보를 찾지 못했습니다."));
        }

        Submission savedSubmission = submissionRepository.save(dto.toSubmission(problem));

        return getSubmissionDto(savedSubmission);
    }

    public void isExistsSubmissionByElementId(String elementId, String username, String site) throws NotFoundException {
        submissionRepository.findByElementIdAndUsernameAndSite(elementId, username, site)
                .orElseThrow(() -> new NotFoundException("제출을 찾지 못했습니다 : " + elementId));
    }


    private boolean isExistsProblem(String problemTitle) {
        return problemRepository.findProblemByProblemTitle(problemTitle).isPresent();
    }

    private SubmissionDto getSubmissionDto(Submission savedSubmission) {
        return SubmissionDto.builder()
                .elementId(savedSubmission.getElementId())
                .submissionTime(savedSubmission.getSubmissionTime())
                .username(savedSubmission.getUsername())
                .problemTitle(savedSubmission.getProblem().getProblemTitle())
                .site(savedSubmission.getProblem().getSite())
                .level(savedSubmission.getProblem().getLevel())
                .link(savedSubmission.getProblem().getLink())
                .build();
    }
}
