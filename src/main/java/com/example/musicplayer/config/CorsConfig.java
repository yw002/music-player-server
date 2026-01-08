package com.example.musicplayer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // 重写跨域映射方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 映射所有请求地址
                .allowedOriginPatterns("http://localhost:*") // 允许前端8080
                .allowedMethods(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
