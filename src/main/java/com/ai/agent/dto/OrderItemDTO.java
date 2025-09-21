package com.ai.agent.dto;

import com.ai.agent.entity.OrderItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderItemDTO {
    
    private Long id;
    
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    
    @Min(value = 1, message = "商品数量必须大于0")
    private Integer quantity;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "商品价格必须大于0")
    private BigDecimal price;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    // 转换为实体
    public OrderItem toEntity() {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(this.quantity);
        orderItem.setPrice(this.price);
        return orderItem;
    }
    
    // 从实体转换
    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        return dto;
    }
}
