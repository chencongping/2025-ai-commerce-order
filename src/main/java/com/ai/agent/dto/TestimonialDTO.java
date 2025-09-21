package com.ai.agent.dto;

import com.ai.agent.entity.Testimonial;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestimonialDTO {
    
    private Long id;
    
    @NotBlank(message = "用户姓名不能为空")
    private String name;
    
    private String avatar;
    
    @Min(value = 1, message = "评分不能低于1分")
    @Max(value = 5, message = "评分不能高于5分")
    private Integer rating;
    
    @NotBlank(message = "评价内容不能为空")
    private String comment;
    
    private String product;
    
    // 转换为实体
    public Testimonial toEntity() {
        Testimonial testimonial = new Testimonial();
        testimonial.setName(this.name);
        testimonial.setAvatar(this.avatar);
        testimonial.setRating(this.rating);
        testimonial.setComment(this.comment);
        testimonial.setProduct(this.product);
        return testimonial;
    }
    
    // 从实体转换
    public static TestimonialDTO fromEntity(Testimonial testimonial) {
        TestimonialDTO dto = new TestimonialDTO();
        dto.setId(testimonial.getId());
        dto.setName(testimonial.getName());
        dto.setAvatar(testimonial.getAvatar());
        dto.setRating(testimonial.getRating());
        dto.setComment(testimonial.getComment());
        dto.setProduct(testimonial.getProduct());
        return dto;
    }
}