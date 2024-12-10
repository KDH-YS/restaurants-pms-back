// src/main/java/com/mysite/config/CorsConfig.java
	package com.mysite.restaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 localhost:3000에서 오는 요청을 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                .allowedHeaders("*");  // 허용할 헤더
    }
    
}
