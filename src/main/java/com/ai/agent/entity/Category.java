package com.ai.agent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    
    @NotBlank(message = "分类名称不能为空")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "icon")
    private String icon;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "count", nullable = false)
    private Integer count = 0;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
}