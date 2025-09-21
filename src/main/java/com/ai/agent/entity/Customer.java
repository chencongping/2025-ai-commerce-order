package com.ai.agent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "orders")
@Entity
@Table(name = "customers")
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {
    
    @NotBlank(message = "客户姓名不能为空")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Email(message = "邮箱格式不正确")
    @Column(name = "email", unique = true)
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address")
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    public enum CustomerStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
