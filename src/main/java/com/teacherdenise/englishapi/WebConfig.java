package com.teacherdenise.englishapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Libera TODOS os endpoints da API
                .allowedOrigins("http://localhost:5173") // Endereço do React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Libera todas as ações
    }
}