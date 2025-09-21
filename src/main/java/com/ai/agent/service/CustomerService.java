package com.ai.agent.service;

import com.ai.agent.dto.CustomerDTO;
import com.ai.agent.entity.Customer;
import com.ai.agent.repository.CustomerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@CircuitBreaker(name = "customerService", fallbackMethod = "fallback")
@Retry(name = "customerService", fallbackMethod = "fallback")
public class CustomerService {
    
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    
    private final Resilience4jFallbackService fallbackService;
    
    private final CustomerRepository customerRepository;
    
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating customer: {}", customerDTO.getName());
        
        // 检查邮箱是否已存在
        if (customerDTO.getEmail() != null && customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("邮箱已存在: " + customerDTO.getEmail());
        }
        
        Customer customer = customerDTO.toEntity();
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully with ID: {}", savedCustomer.getId());
        
        return CustomerDTO.fromEntity(savedCustomer);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers");
        return customerRepository.findAll().stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomerById(Long id) {
        log.info("Fetching customer by ID: {}", id);
        return customerRepository.findById(id)
                .map(CustomerDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        log.info("Fetching customer by email: {}", email);
        return customerRepository.findByEmail(email)
                .map(CustomerDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomers(String keyword) {
        log.info("Searching customers with keyword: {}", keyword);
        return customerRepository.searchCustomers(keyword).stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        log.info("Updating customer with ID: {}", id);
        
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("客户不存在: " + id));
        
        // 检查邮箱是否被其他客户使用
        if (customerDTO.getEmail() != null && !customerDTO.getEmail().equals(existingCustomer.getEmail())) {
            if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
                throw new RuntimeException("邮箱已被其他客户使用: " + customerDTO.getEmail());
            }
        }
        
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setStatus(customerDTO.getStatus());
        
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer updated successfully");
        
        return CustomerDTO.fromEntity(updatedCustomer);
    }
    
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("客户不存在: " + id));
        
        // 检查是否有未完成的订单
        if (!customer.getOrders().isEmpty()) {
            boolean hasActiveOrders = customer.getOrders().stream()
                    .anyMatch(order -> order.getStatus() != com.ai.agent.entity.Order.OrderStatus.DELIVERED && 
                                     order.getStatus() != com.ai.agent.entity.Order.OrderStatus.CANCELLED);
            if (hasActiveOrders) {
                throw new RuntimeException("客户有未完成的订单，无法删除");
            }
        }
        
        customerRepository.delete(customer);
        log.info("Customer deleted successfully");
    }
    
    // Fallback methods
    public List<CustomerDTO> fallback(Throwable throwable) {
        return fallbackService.getAllCustomersFallback(throwable);
    }
    
    public Optional<CustomerDTO> fallback(Long id, Throwable throwable) {
        return fallbackService.getCustomerByIdFallback(id, throwable);
    }
    
    public Optional<CustomerDTO> fallback(String email, Throwable throwable) {
        return fallbackService.getCustomerByEmailFallback(email, throwable);
    }
    
//    public List<CustomerDTO> fallback(String keyword, Throwable throwable) {
//        return fallbackService.searchCustomersFallback(keyword, throwable);
//    }
    
    public CustomerDTO fallback(CustomerDTO customerDTO, Throwable throwable) {
        return fallbackService.createCustomerFallback(customerDTO, throwable);
    }
    
    public CustomerDTO fallback(Long id, CustomerDTO customerDTO, Throwable throwable) {
        return fallbackService.updateCustomerFallback(id, customerDTO, throwable);
    }
    
//    public void fallback(Long id, Throwable throwable) {
//        fallbackService.deleteCustomerFallback(id, throwable);
//    }
}
