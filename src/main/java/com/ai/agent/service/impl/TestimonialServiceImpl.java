package com.ai.agent.service.impl;

import com.ai.agent.dto.TestimonialDTO;
import com.ai.agent.entity.Testimonial;
import com.ai.agent.repository.TestimonialRepository;
import com.ai.agent.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private static final Logger log = LoggerFactory.getLogger(TestimonialServiceImpl.class);
    
    private final TestimonialRepository testimonialRepository;
    
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
    public List<TestimonialDTO> getLatestTestimonials(Integer limit) {
        log.info("获取最新的 {} 条评价", limit);
        return testimonialRepository.findLatestTestimonials(limit).stream()
                .map(TestimonialDTO::fromEntity)
                .collect(Collectors.toList());
    }
}