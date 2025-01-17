package com.oj_timer.server;

import com.oj_timer.server.controller.web.argumentresolver.resolvers.LoginResolver;
import com.oj_timer.server.controller.web.interceptor.AuthorizationInterceptor;
import com.oj_timer.server.controller.web.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // “*“같은 와일드카드를 사용
                .allowedMethods("GET", "POST") // 허용할 HTTP method
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(0)
                .addPathPatterns("/**");

        registry.addInterceptor(new AuthorizationInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**", "/login", "/register", "/css-files/**");
    }


}