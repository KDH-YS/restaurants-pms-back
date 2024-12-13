package com.mysite.restaurant;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // API 경로에 대해 CORS 설정
                .allowedOrigins("http://localhost:3000")  // 클라이언트 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 인증 정보 허용
        // 추가적인 경로에 대해 CORS 허용
        registry.addMapping("/payment/**")  // /payment/** 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(false);  // 인증된 요청을 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH")  // 허용할 HTTP 메서드
                .allowedHeaders("*");  // 허용할 헤더
        // 이미지를 위한 CORS 설정 추가
        registry.addMapping("/images/**")
                .allowedOrigins("http://localhost:3000")  // 클라이언트 주소
                .allowedMethods("GET")  // 이미지 요청은 GET만 허용
                .allowedHeaders("*");  // 모든 헤더 허용
    }
}
