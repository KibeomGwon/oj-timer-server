package com.oj_timer.server.controller.api;

import com.oj_timer.server.controller.api.auth_jwt.JwtDto;
import com.oj_timer.server.controller.api.auth_jwt.JwtGenerator;
import com.oj_timer.server.controller.web.argumentresolver.annotations.Login;
import com.oj_timer.server.exception_handler.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
@Slf4j
public class AuthorizationController {

    private final JwtGenerator jwtGenerator;

    /**
     * 로그인 되어 있지 않으면 http status 401
     *
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<JwtDto> isLogin(@Login String email) {
        log.info("EMAIL [{}]", email);
        if (email == null) {
            throw new UnauthorizedException("인증되지 않았습니다.");
        }
        JwtDto jwts = jwtGenerator.generate(email);
        return new ResponseEntity<>(jwts, HttpStatus.CREATED);
    }
}
