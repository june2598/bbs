package com.example.demo;

import com.example.demo.web.interceptor.ExecutionTimeInterceptor;
import com.example.demo.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

  public final LoginCheckInterceptor loginCheckInterceptor;
  public final ExecutionTimeInterceptor executionTimeInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    //인증체크
    //와일드카드 패턴의 의미
    // *는 0개 이상의 문자와 일치,  /api/products, (o) /api/members (o)    /api/products/123 (x)
    // **는 0개이상의 경로와 일치
    // ? 는 1개의 문자와 일치

    registry.addInterceptor(loginCheckInterceptor)
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns(
            //디테일한 주소가 상위로 올라와야함.
            "/login",
            "/logout",
            "/members/join",
            "/css/**",
            "/js/**",
            "/img/**",
            "/api/**",
            "/test/**",
            "/error/**",
            "/bbs/*",
            "/bbs",
            "/"
        );

    registry.addInterceptor(executionTimeInterceptor)
        .order(1)
        .addPathPatterns("/**");
  }
}
