package com.ai.agent.dto;

import com.ai.agent.entity.OrderItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    
    private Long id;
    
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    
    @Min(value = 1, message = "商品数量必须大于0")
    private Integer quantity;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "商品价格必须大于0")
    private BigDecimal price;
    
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
