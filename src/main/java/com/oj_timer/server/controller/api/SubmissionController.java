package com.oj_timer.server.controller.api;

import com.oj_timer.server.controller.api.http_util.Response;
import com.oj_timer.server.dto.BaekjoonSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import com.oj_timer.server.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SubmissionController {

    private final SubmissionService submissionService;

    /**
     *
     * @PathVariable elementId
     * @RequestParam username
     * @RequestParam site
     * @return Response<Boolean>()
     */
    @GetMapping("/{elementId}")
    public Response<String> checkExists(@PathVariable String elementId, @RequestParam String username, @RequestParam String site) {
        log.info("SubmissionController.checkExists");
        submissionService.isExistsSubmissionByElementId(elementId, username, site);
        return new Response<String>(HttpStatus.OK, "exists", HttpStatus.OK.value());
    }

    @PostMapping
    public Response<SubmissionDto> createSubmission(@RequestBody BaekjoonSubmissionDto dto) {
        log.info("SubmissionController.createSubmission");
        SubmissionDto savedDto = submissionService.save(dto);
        return new Response<>(HttpStatus.CREATED, savedDto, HttpStatus.CREATED.value());
    }
}
