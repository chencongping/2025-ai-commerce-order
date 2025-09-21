package com.ai.agent.repository;

import com.ai.agent.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // 根据名称查询分类
    Category findByName(String name);
    
    // 检查分类名称是否存在
    boolean existsByName(String name);
}