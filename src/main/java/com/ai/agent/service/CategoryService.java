package com.ai.agent.service;

import com.ai.agent.dto.CategoryDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

@CircuitBreaker(name = "categoryService")
@Retry(name = "categoryService")
public interface CategoryService {
    
    // 获取所有分类
    List<CategoryDTO> getAllCategories();
    
    // 根据ID获取分类
    CategoryDTO getCategoryById(Long id);
    
    // 创建分类
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    // 更新分类
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    
    // 删除分类
    void deleteCategory(Long id);
    
    // 根据名称获取分类
    CategoryDTO getCategoryByName(String name);
}