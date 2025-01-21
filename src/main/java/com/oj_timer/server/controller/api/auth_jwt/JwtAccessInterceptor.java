package com.oj_timer.server.controller.api.auth_jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj_timer.server.controller.web.session.SessionConst;
import com.querydsl.core.util.StringUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String requestURI = request.getRequestURI();

        String token = getTokenFromRequest(request);

        log.info("TOKEN [{}]", token);
        HttpSession session = request.getSession();
        log.info("SESSION [{}][{}]", session, session.getAttribute(SessionConst.LOGIN_MANAGER));
        if (session.getAttribute(SessionConst.LOGIN_MANAGER) != null) {
            log.info("BE LOGIN");
            return true;
        }

        if (StringUtils.isNullOrEmpty(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("NOT EXISTS TOKEN");
            return false;
        }

        if (requestURI.contains("/refresh-jwt")) {
            refreshToken(token, response);
            log.info("REFRESH TOKEN");
            return false;
        }

        String email = resolver.parseJwt(token);

        HttpSession createdSession = request.getSession();
        createdSession.setAttribute(SessionConst.LOGIN_MANAGER, email);
        log.info("COMPLETE AUTHORIZATION [{}]", createdSession.getAttribute(SessionConst.LOGIN_MANAGER));
        return true;
    }

    private void refreshToken(String token, HttpServletResponse response) throws IOException {
        String memberEmail = resolver.parseJwt(token);

        JwtDto jwts = generator.generate(memberEmail);

        String result = (new ObjectMapper()).writeValueAsString(jwts);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7).trim();
        }
        return null;
    }
}
