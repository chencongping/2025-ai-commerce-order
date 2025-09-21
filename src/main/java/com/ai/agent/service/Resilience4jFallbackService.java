package com.ai.agent.service;

import com.ai.agent.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Resilience4j回退方法服务类
 * 为所有服务提供熔断和重试失败时的降级处理
 */
@Component
public class Resilience4jFallbackService {
    
    private static final Logger log = LoggerFactory.getLogger(Resilience4jFallbackService.class);

    // CategoryService回退方法
    public List<CategoryDTO> getAllCategoriesFallback(Throwable throwable) {
        log.warn("获取分类列表失败，使用默认空列表回退", throwable);
        return Collections.emptyList();
    }

    public CategoryDTO getCategoryByIdFallback(Long id, Throwable throwable) {
        log.warn("根据ID获取分类失败，ID: {}", id, throwable);
        // 返回默认分类信息
        CategoryDTO defaultCategory = new CategoryDTO();
        defaultCategory.setId(-1L);
        defaultCategory.setName("默认分类");
        // CategoryDTO没有description字段，使用name字段设置默认消息
        defaultCategory.setName("默认分类 - 服务暂时不可用，请稍后再试");
        return defaultCategory;
    }

    public CategoryDTO createCategoryFallback(CategoryDTO categoryDTO, Throwable throwable) {
        log.warn("创建分类失败，分类名称: {}", categoryDTO.getName(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public CategoryDTO updateCategoryFallback(Long id, CategoryDTO categoryDTO, Throwable throwable) {
        log.warn("更新分类失败，ID: {}, 分类名称: {}", id, categoryDTO.getName(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    // ProductService回退方法
    public List<ProductDTO> getAllProductsFallback(Throwable throwable) {
        log.warn("获取商品列表失败，使用默认空列表回退", throwable);
        return Collections.emptyList();
    }

    public Optional<ProductDTO> getProductByIdFallback(Long id, Throwable throwable) {
        log.warn("根据ID获取商品失败，ID: {}", id, throwable);
        return Optional.empty();
    }

    public ProductDTO createProductFallback(ProductDTO productDTO, Throwable throwable) {
        log.warn("创建商品失败，商品名称: {}", productDTO.getName(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    // OrderService回退方法
    public List<OrderDTO> getAllOrdersFallback(Throwable throwable) {
        log.warn("获取订单列表失败，使用默认空列表回退", throwable);
        return Collections.emptyList();
    }

    public Optional<OrderDTO> getOrderByIdFallback(Long id, Throwable throwable) {
        log.warn("根据ID获取订单失败，ID: {}", id, throwable);
        return Optional.empty();
    }

    public OrderDTO createOrderFallback(OrderDTO orderDTO, Throwable throwable) {
        log.warn("创建订单失败，客户ID: {}", orderDTO.getCustomerId(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public Optional<OrderDTO> getOrderByOrderNumberFallback(String orderNumber, Throwable throwable) {
        log.warn("根据订单号获取订单失败，订单号: {}", orderNumber, throwable);
        return Optional.empty();
    }

    public List<OrderDTO> getOrdersByCustomerFallback(Long customerId, Throwable throwable) {
        log.warn("获取客户订单失败，客户ID: {}", customerId, throwable);
        return Collections.emptyList();
    }

    public OrderDTO updateOrderFallback(Long id, OrderDTO orderDTO, Throwable throwable) {
        log.warn("更新订单失败，ID: {}, 订单号: {}", id, orderDTO.getOrderNumber(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public void deleteOrderFallback(Long id, Throwable throwable) {
        log.warn("删除订单失败，ID: {}", id, throwable);
    }

    // CustomerService回退方法
    public List<CustomerDTO> getAllCustomersFallback(Throwable throwable) {
        log.warn("获取客户列表失败，使用默认空列表回退", throwable);
        return Collections.emptyList();
    }

    public Optional<CustomerDTO> getCustomerByIdFallback(Long id, Throwable throwable) {
        log.warn("根据ID获取客户失败，ID: {}", id, throwable);
        return Optional.empty();
    }

    public CustomerDTO createCustomerFallback(CustomerDTO customerDTO, Throwable throwable) {
        log.warn("创建客户失败，客户名称: {}", customerDTO.getName(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public Optional<CustomerDTO> getCustomerByEmailFallback(String email, Throwable throwable) {
        log.warn("根据邮箱获取客户失败，邮箱: {}", email, throwable);
        return Optional.empty();
    }

    public List<CustomerDTO> searchCustomersFallback(String keyword, Throwable throwable) {
        log.warn("搜索客户失败，关键词: {}", keyword, throwable);
        return Collections.emptyList();
    }

    public CustomerDTO updateCustomerFallback(Long id, CustomerDTO customerDTO, Throwable throwable) {
        log.warn("更新客户失败，ID: {}, 客户名称: {}", id, customerDTO.getName(), throwable);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public void deleteCustomerFallback(Long id, Throwable throwable) {
        log.warn("删除客户失败，ID: {}", id, throwable);
    }

    // TestimonialService回退方法
    public List<TestimonialDTO> handleTestimonialFallback(Exception e) {
        log.warn("获取评价列表失败，使用默认空列表回退", e);
        return Collections.emptyList();
    }

    public TestimonialDTO handleTestimonialByIdFallback(Long id, Exception e) {
        log.warn("根据ID获取评价失败，ID: {}", id, e);
        TestimonialDTO defaultTestimonial = new TestimonialDTO();
        defaultTestimonial.setId(-1L);
        // 使用comment字段替代content字段
        defaultTestimonial.setComment("服务暂时不可用，请稍后再试");
        defaultTestimonial.setRating(5);
        return defaultTestimonial;
    }

    public TestimonialDTO handleCreateTestimonialFallback(TestimonialDTO testimonialDTO, Exception e) {
        log.warn("创建评价失败，评价内容: {}", testimonialDTO.getComment(), e);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public TestimonialDTO handleUpdateTestimonialFallback(Long id, TestimonialDTO testimonialDTO, Exception e) {
        log.warn("更新评价失败，ID: {}, 评价内容: {}", id, testimonialDTO.getComment(), e);
        throw new RuntimeException("服务暂时不可用，请稍后再试");
    }

    public void handleDeleteTestimonialFallback(Exception e) {
        log.warn("删除评价失败，使用回退", e);
    }

    public List<TestimonialDTO> handleTestimonialsByProductFallback(String product, Exception e) {
        log.warn("获取商品评价失败，商品名称: {}", product, e);
        return Collections.emptyList();
    }

    public List<TestimonialDTO> handleTestimonialsByMinRatingFallback(Integer rating, Exception e) {
        log.warn("获取指定评分以上的评价失败，评分: {}", rating, e);
        return Collections.emptyList();
    }
}