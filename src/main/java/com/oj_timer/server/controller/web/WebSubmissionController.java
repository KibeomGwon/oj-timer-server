package com.oj_timer.server.controller.web;

import com.oj_timer.server.controller.web.argumentresolver.annotations.Login;
import com.oj_timer.server.controller.web.paging.PageUtil;
import com.oj_timer.server.dto.ProblemAndSubmissionsDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.repository.query.SubmissionQueryRepository;
import com.oj_timer.server.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSubmissionController {

    private final SubmissionService submissionService;

    @GetMapping
    public String findAll(@Login String email, @ModelAttribute SubmissionSearchCondition condition, Pageable pageable, Model model) {

        if (email == null) {
            log.info("MEBMERID IS NULL");
        } else {
            log.info("MEBMERID = {}, CONDITION = {}", email, condition);
        }

        Page<RecentSubmissionDto> page = submissionService.getRecentSubmissionsPaging(email, condition, pageable);
        PageUtil pageUtil = new PageUtil(page);

        model.addAttribute("paging", page);
        model.addAttribute("pageUtil", pageUtil);

        List<SubmissionQueryRepository.SelectObject> selectObjects = submissionService.getSelectObjects(email);

        model.addAttribute("sites", extractSite(selectObjects));
        model.addAttribute("selectObjects", selectObjects);

        return "main";
    }

    @GetMapping("/{problemId}")
    public String findOne(@Login String email,
                          @PathVariable String problemId, @RequestParam String username, Model model) {

        log.info("MEBMERID = {}, PROBLEMID = {}, USERNAME = {}", email, problemId, username);

        Pageable pageable = PageRequest.of(0, 100);
        ProblemAndSubmissionsDto page = submissionService.findSinglePageByProblemTitleIdAndUsername(problemId, username, pageable);

        model.addAttribute("submissions", page.getSubmissionDtos().getContent());
        model.addAttribute("problem", page.getProblemDto());
        model.addAttribute("totalPage", page.getSubmissionDtos().getTotalPages());
        model.addAttribute("currentPageNum", pageable.getPageNumber());

        return "single-page";
    }

    private List<String> extractSite(List<SubmissionQueryRepository.SelectObject> selectObjects) {
        return new ArrayList<>(selectObjects.stream().map(obj -> obj.getSite()).collect(Collectors.toSet()));
    }


}
