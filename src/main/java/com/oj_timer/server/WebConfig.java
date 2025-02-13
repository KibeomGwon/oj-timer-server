package com.oj_timer.server;

import com.oj_timer.server.controller.api.auth_jwt.JwtAccessInterceptor;
import com.oj_timer.server.controller.web.argumentresolver.resolvers.LoginResolver;
import com.oj_timer.server.controller.web.interceptor.AuthorizationInterceptor;
import com.oj_timer.server.controller.web.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAccessInterceptor jwtAccessInterceptor;
    private final LoginResolver loginResolver;
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // “*“같은 와일드카드를 사용
                .allowedMethods("GET", "POST")// 허용할 HTTP method
                .allowedHeaders("*")
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/*.ico", "/css-files/**");

        registry.addInterceptor(jwtAccessInterceptor)
                .order(2)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/authorizaiton/jwt", "/error", "/api/mail");

        registry.addInterceptor(authorizationInterceptor)
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/css-files/**", "/favicon.ico", "/api/**", "/error");

    }
}