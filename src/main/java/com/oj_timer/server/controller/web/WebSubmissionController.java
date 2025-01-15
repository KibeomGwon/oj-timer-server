package com.oj_timer.server.controller.web;

import com.oj_timer.server.dto.ProblemAndSubmissionsDto;
import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebSubmissionController {

    private final SubmissionService submissionService;

    @GetMapping
    public String findAll(@ModelAttribute SubmissionSearchCondition condition, Pageable pageable, Model model) {

        Page<RecentSubmissionDto> page = submissionService.getRecentSubmissionsPaging(condition, pageable);
        model.addAttribute("content", page.getContent());
        model.addAttribute("pageCount", page.getTotalPages());
        return "main";
    }

    @GetMapping("/{problemId}")
    public String findOne(@PathVariable String problemId, @RequestParam String username, Pageable pageable, Model model) {

        ProblemAndSubmissionsDto page = submissionService.findSinglePageByProblemTitleIdAndUsername(problemId, username, pageable);
        model.addAttribute("submissions", page.getSubmissionDtos().getContent());
        model.addAttribute("problem", page.getProblemDto());
        model.addAttribute("totalPage", page.getSubmissionDtos().getTotalPages());
        model.addAttribute("currentPageNum", pageable.getPageNumber());

        return "single-page";
    }
}
