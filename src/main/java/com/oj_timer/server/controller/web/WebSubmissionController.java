package com.oj_timer.server.controller.web;

import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String findAll(@ModelAttribute SubmissionSearchCondition condition, @RequestParam(required = false) PageRequest request, Model model) {
        Page<RecentSubmissionDto> page = submissionService.getRecentSubmissionsPaging(condition, request);
        model.addAttribute("content", page.getContent());
        model.addAttribute("pageCount", page.getTotalPages());
        return "main";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
