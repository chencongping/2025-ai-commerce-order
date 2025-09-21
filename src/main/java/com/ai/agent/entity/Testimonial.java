package com.ai.agent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "testimonials")
@EqualsAndHashCode(callSuper = true)
public class Testimonial extends BaseEntity {
    
    @NotBlank(message = "用户姓名不能为空")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Min(value = 1, message = "评分不能低于1分")
    @Max(value = 5, message = "评分不能高于5分")
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @NotBlank(message = "评价内容不能为空")
    @Column(name = "comment", columnDefinition = "TEXT", nullable = false)
    private String comment;
    
    @Column(name = "product")
    private String product;
}