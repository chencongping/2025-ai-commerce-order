package com.ai.agent.config;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Resilience4jConfig {

    // 创建全局默认的熔断器配置
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // 失败率阈值，超过后熔断器打开
                .waitDurationInOpenState(Duration.ofMillis(10000)) // 熔断器打开状态的等待时间
                .permittedNumberOfCallsInHalfOpenState(2) // 半开状态下允许的调用次数
                .slidingWindowSize(10) // 滑动窗口大小
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // 基于计数的滑动窗口
                .recordExceptions(Exception.class) // 记录所有异常
                .build();
    }

    // 创建全局默认的重试配置
    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3) // 最大重试次数
                .waitDuration(Duration.ofMillis(500)) // 重试间隔
                .build();
    }

    // 创建全局默认的限流器配置
    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(100) // 每个周期允许的请求数
                .limitRefreshPeriod(Duration.ofSeconds(1)) // 刷新周期
                .timeoutDuration(Duration.ofMillis(500)) // 等待令牌的超时时间
                .build();
    }

    // 创建全局默认的时间限制器配置
    @Bean
    public TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5)) // 超时时间
                .build();
    }

    // 创建全局默认的隔离舱配置
    @Bean
    public BulkheadConfig bulkheadConfig() {
        return BulkheadConfig.custom()
                .maxConcurrentCalls(20) // 最大并发调用数
                .maxWaitDuration(Duration.ofMillis(500)) // 等待可用资源的最大时间
                .build();
    }

    // 为熔断器创建自定义实例配置
    @Bean
    public Map<String, CircuitBreakerConfig> circuitBreakerConfigMap() {
        Map<String, CircuitBreakerConfig> configs = new HashMap<>();
        
        // 为产品服务配置熔断器
        CircuitBreakerConfig productServiceConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(40)
                .waitDurationInOpenState(Duration.ofMillis(8000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(8)
                .build();
        configs.put("productService", productServiceConfig);
        
        // 为订单服务配置熔断器
        CircuitBreakerConfig orderServiceConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(30)
                .waitDurationInOpenState(Duration.ofMillis(15000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowSize(15)
                .build();
        configs.put("orderService", orderServiceConfig);
        
        return configs;
    }

    // 为重试创建自定义实例配置
    @Bean
    public Map<String, RetryConfig> retryConfigMap() {
        Map<String, RetryConfig> configs = new HashMap<>();
        
        // 为产品服务配置重试
        RetryConfig productServiceRetry = RetryConfig.custom()
                .maxAttempts(2)
                .waitDuration(Duration.ofMillis(300))
                .build();
        configs.put("productService", productServiceRetry);
        
        // 为订单服务配置重试
        RetryConfig orderServiceRetry = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(600))
                .build();
        configs.put("orderService", orderServiceRetry);
        
        return configs;
    }


}