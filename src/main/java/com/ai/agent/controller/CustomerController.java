package com.ai.agent.controller;

import com.ai.agent.common.ApiResponse;
import com.ai.agent.dto.CustomerDTO;
import com.ai.agent.service.CustomerService;
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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "客户管理", description = "客户相关API")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping
    @Operation(summary = "创建客户", description = "创建新客户")
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer: {}", customerDTO.getName());
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("客户创建成功", createdCustomer));
    }
    
    @GetMapping
    @Operation(summary = "获取所有客户", description = "获取所有客户列表")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        log.info("Fetching all customers");
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponse.success("获取客户列表成功", customers));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取客户", description = "根据客户ID获取客户详情")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(
            @Parameter(description = "客户ID") @PathVariable Long id) {
        log.info("Fetching customer by ID: {}", id);
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取客户成功", customer.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "客户不存在"));
        }
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "根据邮箱获取客户", description = "根据邮箱地址获取客户信息")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerByEmail(
            @Parameter(description = "客户邮箱") @PathVariable String email) {
        log.info("Fetching customer by email: {}", email);
        Optional<CustomerDTO> customer = customerService.getCustomerByEmail(email);
        if (customer.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取客户成功", customer.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "客户不存在"));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索客户", description = "根据关键词搜索客户")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> searchCustomers(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        log.info("Searching customers with keyword: {}", keyword);
        List<CustomerDTO> customers = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(ApiResponse.success("搜索客户成功", customers));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新客户", description = "更新客户信息")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(
            @Parameter(description = "客户ID") @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer with ID: {}", id);
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(ApiResponse.success("客户更新成功", updatedCustomer));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除客户", description = "删除客户")
    public ResponseEntity<ApiResponse<Object>> deleteCustomer(
            @Parameter(description = "客户ID") @PathVariable Long id) {
        log.info("Deleting customer with ID: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ApiResponse.success("客户删除成功", null));
    }
}
