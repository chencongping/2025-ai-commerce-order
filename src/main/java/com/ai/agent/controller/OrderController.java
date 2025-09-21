package com.ai.agent.controller;

import com.ai.agent.common.ApiResponse;
import com.ai.agent.dto.OrderDTO;
import com.ai.agent.entity.Order;
import com.ai.agent.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "订单管理", description = "订单相关API")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "创建订单", description = "创建新订单")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Creating order for customer ID: {}", orderDTO.getCustomerId());
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("订单创建成功", createdOrder));
    }
    
    @GetMapping
    @Operation(summary = "获取所有订单", description = "获取所有订单列表")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("获取订单列表成功", orders));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取订单", description = "根据订单ID获取订单详情")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        log.info("Fetching order by ID: {}", id);
        Optional<OrderDTO> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取订单成功", order.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "订单不存在"));
        }
    }
    
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "根据订单号获取订单", description = "根据订单号获取订单信息")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderByOrderNumber(
            @Parameter(description = "订单号") @PathVariable String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        Optional<OrderDTO> order = orderService.getOrderByOrderNumber(orderNumber);
        if (order.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取订单成功", order.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "订单不存在"));
        }
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "获取客户订单", description = "根据客户ID获取订单列表")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByCustomerId(
            @Parameter(description = "客户ID") @PathVariable Long customerId) {
        log.info("Fetching orders for customer ID: {}", customerId);
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success("获取客户订单成功", orders));
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态获取订单", description = "根据订单状态获取订单列表")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByStatus(
            @Parameter(description = "订单状态") @PathVariable Order.OrderStatus status) {
        log.info("Fetching orders by status: {}", status);
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("获取状态订单成功", orders));
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态", description = "更新订单状态")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @Parameter(description = "订单ID") @PathVariable Long id,
            @Parameter(description = "新状态") @RequestParam Order.OrderStatus status) {
        log.info("Updating order status for ID: {} to {}", id, status);
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("订单状态更新成功", updatedOrder));
    }
    
    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消订单", description = "取消订单")
    public ResponseEntity<ApiResponse<Object>> cancelOrder(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        log.info("Cancelling order with ID: {}", id);
        orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.success("订单取消成功", null));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单", description = "删除订单")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(
            @Parameter(description = "订单ID") @PathVariable Long id) {
        log.info("Deleting order with ID: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("订单删除成功", null));
    }
}
