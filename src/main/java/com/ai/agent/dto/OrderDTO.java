package com.ai.agent.dto;

import com.ai.agent.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public Order.OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
    
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
