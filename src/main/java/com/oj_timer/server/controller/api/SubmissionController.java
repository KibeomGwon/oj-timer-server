package com.oj_timer.server.controller.api;

import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.SubmissionDto;
import com.oj_timer.server.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> checkExists(@PathVariable String elementId, @RequestParam String username, @RequestParam String site) {
        log.info("SubmissionController.checkExists");
        submissionService.isExistsSubmissionByElementId(elementId, username, site);
        return new ResponseEntity<String>("exists", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubmissionDto> createSubmission(@RequestBody InputSubmissionDto dto) {
        log.info("SubmissionController.createSubmission");
        System.out.println(dto.toString());

        SubmissionDto savedDto = submissionService.save(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
}
