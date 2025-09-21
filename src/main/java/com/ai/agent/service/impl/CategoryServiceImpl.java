package com.ai.agent.service.impl;

import com.ai.agent.dto.CategoryDTO;
import com.ai.agent.entity.Category;
import com.ai.agent.repository.CategoryRepository;
import com.ai.agent.service.CategoryService;
import com.ai.agent.service.Resilience4jFallbackService;
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
@CircuitBreaker(name = "categoryService", fallbackMethod = "fallback")
@Retry(name = "categoryService", fallbackMethod = "fallback")
public class CategoryServiceImpl implements CategoryService {
    
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    
    private final Resilience4jFallbackService fallbackService;
    
    private final CategoryRepository categoryRepository;
    
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在: " + id));
        return CategoryDTO.fromEntity(category);
    }
    
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // 检查分类名称是否已存在
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("分类名称已存在: " + categoryDTO.getName());
        }
        
        Category category = categoryDTO.toEntity();
        Category savedCategory = categoryRepository.save(category);
        return CategoryDTO.fromEntity(savedCategory);
    }
    
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在: " + id));
        
        // 检查新的分类名称是否已存在（排除当前分类）
        if (!category.getName().equals(categoryDTO.getName()) && 
            categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("分类名称已存在: " + categoryDTO.getName());
        }
        
        // 更新分类信息
        category.setName(categoryDTO.getName());
        category.setIcon(categoryDTO.getIcon());
        category.setImageUrl(categoryDTO.getImageUrl());
        category.setCount(categoryDTO.getCount() != null ? categoryDTO.getCount() : 0);
        
        Category updatedCategory = categoryRepository.save(category);
        return CategoryDTO.fromEntity(updatedCategory);
    }
    
    @Override
    @CircuitBreaker(name = "categoryService", fallbackMethod = "deleteFallback")
    @Retry(name = "categoryService", fallbackMethod = "deleteFallback")
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("分类不存在: " + id);
        }
        categoryRepository.deleteById(id);
    }
    
    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new RuntimeException("分类不存在: " + name);
        }
        return CategoryDTO.fromEntity(category);
    }
    
    // 回退方法
    public List<CategoryDTO> fallback(Throwable throwable) {
        return fallbackService.getAllCategoriesFallback(throwable);
    }

//    public CategoryDTO fallback(Long id, Throwable throwable) {
//        return fallbackService.getCategoryByIdFallback(id, throwable);
//    }

    public CategoryDTO fallback(CategoryDTO categoryDTO, Throwable throwable) {
        return fallbackService.createCategoryFallback(categoryDTO, throwable);
    }

    public CategoryDTO fallback(Long id, CategoryDTO categoryDTO, Throwable throwable) {
        return fallbackService.updateCategoryFallback(id, categoryDTO, throwable);
    }

    public void deleteFallback(Long id, Throwable throwable) {
        log.warn("删除分类失败，ID: {}", id, throwable);
    }
}