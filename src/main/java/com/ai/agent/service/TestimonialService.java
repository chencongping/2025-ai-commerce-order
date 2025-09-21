package com.ai.agent.service;

import com.ai.agent.dto.TestimonialDTO;

import java.util.List;

public interface TestimonialService {
    
    // 获取所有评价
    List<TestimonialDTO> getAllTestimonials();
    
    // 根据ID获取评价
    TestimonialDTO getTestimonialById(Long id);
    
    // 创建评价
    TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO);
    
    // 更新评价
    TestimonialDTO updateTestimonial(Long id, TestimonialDTO testimonialDTO);
    
    // 删除评价
    void deleteTestimonial(Long id);
    
    // 根据商品名称获取评价
    List<TestimonialDTO> getTestimonialsByProduct(String product);
    
    // 获取评分大于等于指定值的评价
    List<TestimonialDTO> getTestimonialsByMinRating(Integer rating);
    
    // 获取最新的N条评价
    List<TestimonialDTO> getLatestTestimonials(Integer limit);
}