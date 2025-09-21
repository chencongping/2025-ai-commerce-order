package com.ai.agent.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS配置类 - 解决跨域资源共享问题
 * 允许来自不同来源的前端应用访问后端API
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 允许所有路径（包括Swagger）跨域，生产环境可缩小范围
                registry.addMapping("/**")
                        // 允许的前端域名/端口，生产环境需指定具体域名
                        .allowedOrigins("http://localhost:3000", "http://localhost:3002", "http://192.168.50.126:3002")
                        // 允许的请求方法
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // 允许的请求头
                        .allowedHeaders("*")
                        // 允许携带Cookie（如需）
                        .allowCredentials(true)
                        // 预检请求有效期（秒）
                        .maxAge(3600);
            }
        };
    }
}