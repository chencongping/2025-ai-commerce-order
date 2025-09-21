package com.ai.agent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

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
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getProduct() {
        return product;
    }
    
    public void setProduct(String product) {
        this.product = product;
    }
}