package com.oj_timer.server.controller.api;

import com.oj_timer.server.controller.web.argumentresolver.annotations.Login;
import com.oj_timer.server.dto.InputSubmissionDto;
import com.oj_timer.server.dto.domain.SubmissionDto;
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
    public ResponseEntity<String> checkExists(@Login String email, @PathVariable String elementId, @RequestParam String username, @RequestParam String site) {
        log.info("EMAIL {}, ELEMENTID {}, USERNAME {}, SITE {}", email, elementId, username, site);
        submissionService.existsSubmissionByElementId(email, elementId, username, site);
        return new ResponseEntity<>("exists", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubmissionDto> createSubmission(@Login String email, @RequestBody InputSubmissionDto dto) {
        SubmissionDto savedDto = submissionService.save(email, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
}
