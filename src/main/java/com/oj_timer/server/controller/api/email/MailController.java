package com.oj_timer.server.controller.api.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final EmailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<Integer> mailAuth(@RequestBody MailDto dto) {
        int number = mailService.sendMail(dto.getMail());

        return new ResponseEntity<>(number, HttpStatus.CREATED);
    }
}
