package com.ai.agent.dto;

import com.ai.agent.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    
    private Long id;
    private String orderNumber;
    
    @NotNull(message = "客户ID不能为空")
    private Long customerId;
    
    private Order.OrderStatus status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String notes;
    
    @Valid
    private List<OrderItemDTO> orderItems;
    
    // 转换为实体
    public Order toEntity() {
        Order order = new Order();
        order.setOrderNumber(this.orderNumber);
        order.setStatus(this.status != null ? this.status : Order.OrderStatus.PENDING);
        order.setTotalAmount(this.totalAmount != null ? this.totalAmount : BigDecimal.ZERO);
        order.setShippingAddress(this.shippingAddress);
        order.setNotes(this.notes);
        return order;
    }
    
    // 从实体转换
    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setNotes(order.getNotes());
        return dto;
    }
}
