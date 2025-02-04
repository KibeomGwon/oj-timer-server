package com.oj_timer.server.controller.api;

import com.oj_timer.server.controller.api.auth_jwt.JwtDto;
import com.oj_timer.server.controller.api.auth_jwt.JwtGenerator;
import com.oj_timer.server.controller.web.argumentresolver.annotations.Login;
import com.oj_timer.server.controller.web.session.SessionConst;
import com.oj_timer.server.exception_handler.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
@Slf4j
public class AuthorizationController {

    private final JwtGenerator jwtGenerator;
    /**
     * refresh 는 interceptor 에서 처리 "/api/refresh-jwt" => 나중에 할 때는 그냥 컨트롤러에서 처리해야겠다.
     */


    /**
     * 로그인 되어 있지 않으면 http status 401
     *
     * @return ResponseEntity
     */
    @GetMapping("/jwts")
    public ResponseEntity<JwtDto> getJwts(@Login String email) {
        log.info("EMAIL [{}]", email);
        if (email == null) {
            throw new UnauthorizedException("인증되지 않았습니다.");
        }
        JwtDto jwts = jwtGenerator.generate(email);
        return new ResponseEntity<>(jwts, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> isLogin(@Login String email) {
        log.info("EMAIL [{}]", email);
        if (email == null) {
            throw new UnauthorizedException("인증되지 않았습니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
