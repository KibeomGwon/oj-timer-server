package com.oj_timer.server.service;

import com.oj_timer.server.dto.*;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.domain.ProblemDto;
import com.oj_timer.server.dto.domain.SubmissionDto;
import com.oj_timer.server.entity.Member;
import com.oj_timer.server.exception_handler.exceptions.BadRequestException;
import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.repository.MemberRepository;
import com.oj_timer.server.repository.ProblemRepository;
import com.oj_timer.server.repository.SubmissionRepository;
import com.oj_timer.server.repository.mybatis.RecentSubmissionRepository;
import com.oj_timer.server.repository.query.SubmissionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionQueryRepository submissionQueryRepository;
    private final MemberRepository memberRepository;
    private final RecentSubmissionRepository recentSubmissionRepository;

    public SubmissionDto save(String email, InputSubmissionDto dto) throws BadRequestException {
        Problem problem = dto.toProblem();

        problem = findOrCreateProblem(problem);

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("유저를 찾지 못했습니다."));

        Submission submission = dto.toSubmission();
        submission.bindingProblem(problem);
        submission.solveFrom(member);

        // have to modify
        Submission savedSubmission = submissionRepository.save(submission);

        return SubmissionDto.toDto(savedSubmission);
    }

    public void existsSubmissionByElementId(String email, String elementId, String username, String site) throws NotFoundException {
        submissionRepository.findByElementIdAndUsernameAndSite(elementId, username, site, email)
                .orElseThrow(() -> new NotFoundException("제출을 찾지 못했습니다 : " + elementId));
    }

    public Page<RecentSubmissionDto> getRecentSubmissionsPaging(String email, SubmissionSearchCondition condition, Pageable pageable) {
        condition.setEmail(email);
        return recentSubmissionRepository.findRecentSubmissions(condition, pageable);
    }


    public ProblemAndSubmissionsDto findSinglePageByProblemTitleIdAndUsername(String problemTitleId, String username, Pageable pageable) {
        Problem problem = problemRepository
                .findProblemByProblemTitleId(problemTitleId).orElseThrow(() -> new NotFoundException("문제를 찾지 못했습니다." + problemTitleId));
        log.info("PROBLEM {}", problem.toString());

        Page<SubmissionDto> submissionDtos = submissionRepository.findAllByProblemTitleIdAndUsername(problemTitleId, username, pageable);
        log.info("SUBMISSIONS SIZE {}", submissionDtos.getContent().size());

        return ProblemAndSubmissionsDto.create(ProblemDto.toDto(problem), submissionDtos);
    }

    public List<SubmissionQueryRepository.SelectObject> getSelectObjects(String email) {
        return submissionQueryRepository.getSelectObjects(email).getSelectObjects();
    }


    // === private method === //
    private Problem findOrCreateProblem(Problem problem) {
        return problemRepository.findProblemByProblemTitleId(problem.getProblemTitleId())
                .orElseGet(() -> problemRepository.save(problem));
    }
}
