package com.ai.agent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
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
}