package com.oj_timer.server.controller.web.interceptor;

import com.oj_timer.server.controller.web.session.SessionConst;
import com.oj_timer.server.exception_handler.BaseCustomException;
import com.oj_timer.server.exception_handler.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        log.info("AUTHORIZATION [{}][{}][{}]",
                requestURI,
                session != null ? session.getAttribute(SessionConst.LOGIN_MANAGER) : null,
                request.getCookies() != null ?  request.getCookies()[0].toString() : null
        );

        if (session == null || session.getAttribute(SessionConst.LOGIN_MANAGER) == null) {
            log.info("FAILED AUTHORIZATION [{}]", requestURI);
            if (requestURI.contains("/api")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                response.sendRedirect("/login?redirectURL=" + requestURI);
            }
            return false;
        }

        return true;
    }
}
