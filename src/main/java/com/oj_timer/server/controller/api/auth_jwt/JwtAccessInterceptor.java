package com.oj_timer.server.controller.api.auth_jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj_timer.server.controller.web.session.SessionConst;
import com.oj_timer.server.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAccessInterceptor implements HandlerInterceptor {

    private final JwtResolver resolver;
    private final JwtGenerator generator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // preflight로 인한 에러를 막기위한 로직
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String requestURI = request.getRequestURI();

        String token = resolver.getTokenFromRequest(request);

        HttpSession session = request.getSession();

        if (session.getAttribute(SessionConst.LOGIN_MANAGER) != null) {
            log.info("SESSION [{}]", session.getAttribute(SessionConst.LOGIN_MANAGER));
            log.info("BE LOGIN");
            return true;
        }

        if (requestURI.contains("/refresh-jwt")) {
            return refreshToken(token, response);
        }

        if (!resolver.validation(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("UNAUTHORIZED TOKEN");
            return false;
        }

        String email = resolver.parseJwt(token);

        if (refreshTokenRepository.findOne(email) == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("UNAUTHORIZED TOKEN");
            return false;
        }

        return true;
    }

    private boolean refreshToken(String token, HttpServletResponse response) throws IOException {

        if (!resolver.validation(token)) {
            refreshTokenRepository.deleteByToken(token);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("UNAUTHORIZED TOKEN IN REFRESH");
            return false;
        }

        String memberEmail = resolver.parseJwt(token);

        // cache에 없거나 refresh token이 일치하지 않을 때
        if (refreshTokenRepository.findOne(memberEmail) == null || !refreshTokenRepository.findOne(memberEmail).equals(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        JwtDto jwts = generator.generate(memberEmail);

        String result = (new ObjectMapper()).writeValueAsString(jwts);


        // controller로 가지않고 interceptor에서 바로 응답을 보내기 위한 로직
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);

        log.info("REFRESH COMPLETE");
        return false;
    }
}