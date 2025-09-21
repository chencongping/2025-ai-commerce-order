package com.ai.agent.service;

import com.ai.agent.dto.OrderDTO;
import com.ai.agent.dto.OrderItemDTO;
import com.ai.agent.entity.*;
import com.ai.agent.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("Creating order for customer ID: {}", orderDTO.getCustomerId());
        
        // 验证客户是否存在
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("客户不存在: " + orderDTO.getCustomerId()));
        
        // 创建订单
        Order order = orderDTO.toEntity();
        order.setCustomer(customer);
        order.setOrderNumber(generateOrderNumber());
        // 打印订单所有的信息
        log.info("Order details: {}", order);
        // 保存订单
        Order savedOrder = orderRepository.save(order);
        
        // 处理订单项
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (orderDTO.getOrderItems() != null && !orderDTO.getOrderItems().isEmpty()) {
            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = createOrderItem(savedOrder, itemDTO);
                totalAmount = totalAmount.add(orderItem.getSubtotal());
            }
        }
        
        // 更新订单总金额
        savedOrder.setTotalAmount(totalAmount);
        Order finalOrder = orderRepository.save(savedOrder);
        // 打印订单信息
        log.info("Order details: {}", finalOrder);
        return OrderDTO.fromEntity(finalOrder);
    }
    
    private OrderItem createOrderItem(Order order, OrderItemDTO itemDTO) {
        // 验证商品是否存在
        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("商品不存在: " + itemDTO.getProductId()));
        
        // 检查库存
        if (product.getStockQuantity() < itemDTO.getQuantity()) {
            throw new RuntimeException("商品库存不足: " + product.getName() + 
                    " (需要: " + itemDTO.getQuantity() + ", 可用: " + product.getStockQuantity() + ")");
        }
        
        // 创建订单项
        OrderItem orderItem = itemDTO.toEntity();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice()); // 使用商品当前价格
        
        // 保存订单项
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        
        // 更新商品库存
        product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
        if (product.getStockQuantity() == 0) {
            product.setStatus(Product.ProductStatus.OUT_OF_STOCK);
        }
        productRepository.save(product);
        
        return savedOrderItem;
    }
    
    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD" + timestamp;
    }
    
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<OrderDTO> getOrderById(Long id) {
        log.info("Fetching order by ID: {}", id);
        return orderRepository.findById(id)
                .map(OrderDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public Optional<OrderDTO> getOrderByOrderNumber(String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber)
                .map(OrderDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        log.info("Fetching orders for customer ID: {}", customerId);
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(Order.OrderStatus status) {
        log.info("Fetching orders by status: {}", status);
        return orderRepository.findByStatus(status).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public OrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        log.info("Updating order status for ID: {} to {}", id, status);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + id));
        
        // 状态转换验证
        if (!isValidStatusTransition(order.getStatus(), status)) {
            throw new RuntimeException("无效的状态转换: " + order.getStatus() + " -> " + status);
        }
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        
        log.info("Order status updated successfully");
        return OrderDTO.fromEntity(updatedOrder);
    }
    
    private boolean isValidStatusTransition(Order.OrderStatus from, Order.OrderStatus to) {
        // 定义允许的状态转换
        switch (from) {
            case PENDING:
                return to == Order.OrderStatus.CONFIRMED || to == Order.OrderStatus.CANCELLED;
            case CONFIRMED:
                return to == Order.OrderStatus.SHIPPED || to == Order.OrderStatus.CANCELLED;
            case SHIPPED:
                return to == Order.OrderStatus.DELIVERED;
            case DELIVERED:
                return to == Order.OrderStatus.REFUNDED;
            case CANCELLED:
            case REFUNDED:
                return false; // 终态，不能转换
            default:
                return false;
        }
    }
    
    public void cancelOrder(Long id) {
        log.info("Cancelling order with ID: {}", id);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + id));
        
        if (order.getStatus() != Order.OrderStatus.PENDING && order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new RuntimeException("只能取消待确认或已确认的订单");
        }
        
        // 恢复商品库存
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            if (product.getStatus() == Product.ProductStatus.OUT_OF_STOCK) {
                product.setStatus(Product.ProductStatus.ACTIVE);
            }
            productRepository.save(product);
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
        
        log.info("Order cancelled successfully");
    }
    
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + id));
        
        // 只能删除已取消或已退款的订单
        if (order.getStatus() != Order.OrderStatus.CANCELLED && order.getStatus() != Order.OrderStatus.REFUNDED) {
            throw new RuntimeException("只能删除已取消或已退款的订单");
        }
        
        orderRepository.delete(order);
        log.info("Order deleted successfully");
    }
}
