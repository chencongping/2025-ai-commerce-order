package com.ai.agent.dto;

import com.ai.agent.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerDTO {
    
    private Long id;
    
    @NotBlank(message = "客户姓名不能为空")
    private String name;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    private String address;
    
    private Customer.CustomerStatus status;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Customer.CustomerStatus getStatus() {
        return status;
    }
    
    public void setStatus(Customer.CustomerStatus status) {
        this.status = status;
    }
    
    // 转换为实体
    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setName(this.name);
        customer.setEmail(this.email);
        customer.setPhone(this.phone);
        customer.setAddress(this.address);
        customer.setStatus(this.status != null ? this.status : Customer.CustomerStatus.ACTIVE);
        return customer;
    }
    
    // 从实体转换
    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setStatus(customer.getStatus());
        return dto;
    }
}
