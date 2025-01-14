package com.oj_timer.server.service;

import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.exception_handler.exceptions.BadRequestException;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.repository.ProblemRepository;
import com.oj_timer.server.repository.SubmissionRepository;
import com.oj_timer.server.repository.query.SubmissionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionQueryRepository submissionQueryRepository;

    public SubmissionDto save(InputSubmissionDto dto) throws BadRequestException {
        Problem problem = dto.toProblem();

        if (!isExistsProblem(problem.getProblemTitleId())) {
            problemRepository.save(problem);
        } else {
            problem = problemRepository.findProblemByProblemTitleId(problem.getProblemTitleId())
                    .orElseThrow(() -> new BadRequestException("문제 정보를 찾지 못했습니다."));
        }

        Submission savedSubmission = submissionRepository.save(dto.toSubmission(problem));

        return getSubmissionDto(savedSubmission);
    }

    public void isExistsSubmissionByElementId(String elementId, String username, String site) throws NotFoundException {
        submissionRepository.findByElementIdAndUsernameAndSite(elementId, username, site)
                .orElseThrow(() -> new NotFoundException("제출을 찾지 못했습니다 : " + elementId));
    }

    public Page<RecentSubmissionDto> getRecentSubmissionsPaging(SubmissionSearchCondition condition, Pageable pageable) {
        return submissionQueryRepository.findRecentSubmissionPage(condition, pageable);
    }


    private boolean isExistsProblem(String problemTitle) {
        return problemRepository.findProblemByProblemTitleId(problemTitle).isPresent();
    }

    private SubmissionDto getSubmissionDto(Submission savedSubmission) {
        return SubmissionDto.builder()
                .elementId(savedSubmission.getElementId())
                .submissionTime(savedSubmission.getSubmissionTime())
                .username(savedSubmission.getUsername())
                .problemTitle(savedSubmission.getProblem().getProblemTitleId())
                .site(savedSubmission.getProblem().getSite())
                .level(savedSubmission.getProblem().getLevel())
                .link(savedSubmission.getProblem().getLink())
                .build();
    }
}
