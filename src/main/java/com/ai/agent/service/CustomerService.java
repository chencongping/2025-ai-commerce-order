package com.ai.agent.service;

import com.ai.agent.dto.CustomerDTO;
import com.ai.agent.entity.Customer;
import com.ai.agent.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {
    
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
}
