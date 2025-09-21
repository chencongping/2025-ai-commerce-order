package com.ai.agent.dto;

import com.ai.agent.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "分类名称不能为空")
    private String name;
    
    private String icon;
    
    private String imageUrl;
    
    private Integer count;
    
    // 转换为实体
    public Category toEntity() {
        Category category = new Category();
        category.setName(this.name);
        category.setIcon(this.icon);
        category.setImageUrl(this.imageUrl);
        category.setCount(this.count != null ? this.count : 0);
        return category;
    }
    
    // 从实体转换
    public static CategoryDTO fromEntity(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIcon(category.getIcon());
        dto.setImageUrl(category.getImageUrl());
        dto.setCount(category.getCount());
        return dto;
    }
}