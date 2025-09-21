package com.ai.agent.service.impl;

import com.ai.agent.dto.TestimonialDTO;
import com.ai.agent.entity.Testimonial;
import com.ai.agent.repository.TestimonialRepository;
import com.ai.agent.service.Resilience4jFallbackService;
import com.ai.agent.service.TestimonialService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@CircuitBreaker(name = "testimonialService", fallbackMethod = "fallback")
@Retry(name = "testimonialService", fallbackMethod = "fallback")
public class TestimonialServiceImpl implements TestimonialService {
    
    private final Resilience4jFallbackService fallbackService;
    private static final Logger log = LoggerFactory.getLogger(TestimonialServiceImpl.class);
    private final TestimonialRepository testimonialRepository;
    
    // Fallback method for Resilience4j
    public List<TestimonialDTO> fallback(Exception e) {
        return fallbackService.handleTestimonialFallback(e);
    }
    
    public TestimonialDTO fallback(Long id, Exception e) {
        return fallbackService.handleTestimonialByIdFallback(id, e);
    }
    
    public TestimonialDTO fallback(TestimonialDTO testimonialDTO, Exception e) {
        return fallbackService.handleCreateTestimonialFallback(testimonialDTO, e);
    }
    
    public TestimonialDTO fallback(Long id, TestimonialDTO testimonialDTO, Exception e) {
        return fallbackService.handleUpdateTestimonialFallback(id, testimonialDTO, e);
    }
    
    public void fallback(Void unused, Exception e) {
        fallbackService.handleDeleteTestimonialFallback(e);
    }
    
    public List<TestimonialDTO> fallback(String product, Exception e) {
        return fallbackService.handleTestimonialsByProductFallback(product, e);
    }
    
    public List<TestimonialDTO> fallback(Integer rating, Exception e) {
        return fallbackService.handleTestimonialsByMinRatingFallback(rating, e);
    }
    

    
    @Override
    public List<TestimonialDTO> getAllTestimonials() {
        return testimonialRepository.findAll().stream()
                .map(TestimonialDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public TestimonialDTO getTestimonialById(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("评价不存在: " + id));
        return TestimonialDTO.fromEntity(testimonial);
    }
    
    @Override
    public TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO) {
        Testimonial testimonial = testimonialDTO.toEntity();
        Testimonial savedTestimonial = testimonialRepository.save(testimonial);
        return TestimonialDTO.fromEntity(savedTestimonial);
    }
    
    @Override
    public TestimonialDTO updateTestimonial(Long id, TestimonialDTO testimonialDTO) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("评价不存在: " + id));
        
        // 更新评价信息
        testimonial.setName(testimonialDTO.getName());
        testimonial.setAvatar(testimonialDTO.getAvatar());
        testimonial.setRating(testimonialDTO.getRating());
        testimonial.setComment(testimonialDTO.getComment());
        testimonial.setProduct(testimonialDTO.getProduct());
        
        Testimonial updatedTestimonial = testimonialRepository.save(testimonial);
        return TestimonialDTO.fromEntity(updatedTestimonial);
    }
    
    @Override
    public void deleteTestimonial(Long id) {
        if (!testimonialRepository.existsById(id)) {
            throw new RuntimeException("评价不存在: " + id);
        }
        testimonialRepository.deleteById(id);
    }
    
    @Override
    public List<TestimonialDTO> getTestimonialsByProduct(String product) {
        return testimonialRepository.findByProduct(product).stream()
                .map(TestimonialDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TestimonialDTO> getTestimonialsByMinRating(Integer rating) {
        return testimonialRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(TestimonialDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "testimonialService", fallbackMethod = "getLatestTestimonialsFallback")
    @Retry(name = "testimonialService", fallbackMethod = "getLatestTestimonialsFallback")
    public List<TestimonialDTO> getLatestTestimonials(Integer limit) {
        log.info("获取最新的 {} 条评价", limit);
        return testimonialRepository.findLatestTestimonials(limit).stream()
                .map(TestimonialDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    // 专门针对getLatestTestimonials方法的fallback
    public List<TestimonialDTO> getLatestTestimonialsFallback(Integer limit, Exception e) {
        return fallbackService.handleTestimonialFallback(e);
    }
}