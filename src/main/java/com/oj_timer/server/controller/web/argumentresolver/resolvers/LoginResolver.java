package com.oj_timer.server.controller.web.argumentresolver.resolvers;

import com.oj_timer.server.controller.api.auth_jwt.JwtResolver;
import com.oj_timer.server.controller.web.argumentresolver.annotations.Login;
import com.oj_timer.server.controller.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginResolver implements HandlerMethodArgumentResolver {

    private final JwtResolver jwtResolver;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter");

        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = String.class.isAssignableFrom(parameter.getParameterType());


        return hasParameterAnnotation && assignableFrom;  // 리턴 값이 true 면 resolveArgument() 실행
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
    {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);


        String token = jwtResolver.getTokenFromRequest(request);

        // session 만 필요할 때 session 만 받는 일이 있고, token 만 필요할 때 token 만 받는 일도 있다.
        if (session == null && token == null)
            return null;

        String email = null;

        if (jwtResolver.validation(token)) {
            email = jwtResolver.parseJwt(token);
        }

        log.info("resolveArgument {}", email != null ? email : session.getAttribute(SessionConst.LOGIN_MANAGER));

        return email != null ? email : session.getAttribute(SessionConst.LOGIN_MANAGER);
    }
}
