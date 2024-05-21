package com.mlog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("http://localhost:5173", "http://m-log.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 모든 HTTP 메소드 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 쿠키를 포함한 요청 허용
    }
}