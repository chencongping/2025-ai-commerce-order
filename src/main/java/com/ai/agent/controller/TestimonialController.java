package com.ai.agent.controller;

import com.ai.agent.dto.TestimonialDTO;
import com.ai.agent.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {
    
    private final TestimonialService testimonialService;
    
    // 获取所有评价
    @GetMapping
    public ResponseEntity<List<TestimonialDTO>> getAllTestimonials() {
        List<TestimonialDTO> testimonials = testimonialService.getAllTestimonials();
        return ResponseEntity.ok(testimonials);
    }
    
    // 根据ID获取评价
    @GetMapping("/{id}")
    public ResponseEntity<TestimonialDTO> getTestimonialById(@PathVariable Long id) {
        TestimonialDTO testimonial = testimonialService.getTestimonialById(id);
        return ResponseEntity.ok(testimonial);
    }
    
    // 创建评价
    @PostMapping
    public ResponseEntity<TestimonialDTO> createTestimonial(@RequestBody TestimonialDTO testimonialDTO) {
        TestimonialDTO createdTestimonial = testimonialService.createTestimonial(testimonialDTO);
        return new ResponseEntity<>(createdTestimonial, HttpStatus.CREATED);
    }
    
    // 更新评价
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDTO> updateTestimonial(@PathVariable Long id, @RequestBody TestimonialDTO testimonialDTO) {
        TestimonialDTO updatedTestimonial = testimonialService.updateTestimonial(id, testimonialDTO);
        return ResponseEntity.ok(updatedTestimonial);
    }
    
    // 删除评价
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
    
    // 根据商品名称获取评价
    @GetMapping("/product/{product}")
    public ResponseEntity<List<TestimonialDTO>> getTestimonialsByProduct(@PathVariable String product) {
        List<TestimonialDTO> testimonials = testimonialService.getTestimonialsByProduct(product);
        return ResponseEntity.ok(testimonials);
    }
    
    // 获取评分大于等于指定值的评价
    @GetMapping("/min-rating/{rating}")
    public ResponseEntity<List<TestimonialDTO>> getTestimonialsByMinRating(@PathVariable Integer rating) {
        List<TestimonialDTO> testimonials = testimonialService.getTestimonialsByMinRating(rating);
        return ResponseEntity.ok(testimonials);
    }
    
    // 获取最新的N条评价
    @GetMapping("/latest/{limit}")
    public ResponseEntity<List<TestimonialDTO>> getLatestTestimonials(@PathVariable Integer limit) {
        List<TestimonialDTO> testimonials = testimonialService.getLatestTestimonials(limit);
        return ResponseEntity.ok(testimonials);
    }
}