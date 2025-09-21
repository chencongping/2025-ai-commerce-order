package com.ai.agent.configs;

import com.ai.agent.entity.Customer;
import com.ai.agent.entity.Product;
import com.ai.agent.repository.CustomerRepository;
import com.ai.agent.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("开始初始化测试数据...");
        
        // 初始化客户数据
        if (customerRepository.count() == 0) {
            createCustomers();
        }
        
        // 初始化商品数据
        if (productRepository.count() == 0) {
            createProducts();
        }
        
        log.info("测试数据初始化完成");
    }
    
    private void createCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("张三");
        customer1.setEmail("zhangsan@example.com");
        customer1.setPhone("13800138001");
        customer1.setAddress("北京市朝阳区");
        customer1.setStatus(Customer.CustomerStatus.ACTIVE);
        customerRepository.save(customer1);
        
        Customer customer2 = new Customer();
        customer2.setName("李四");
        customer2.setEmail("lisi@example.com");
        customer2.setPhone("13800138002");
        customer2.setAddress("上海市浦东新区");
        customer2.setStatus(Customer.CustomerStatus.ACTIVE);
        customerRepository.save(customer2);
        
        Customer customer3 = new Customer();
        customer3.setName("王五");
        customer3.setEmail("wangwu@example.com");
        customer3.setPhone("13800138003");
        customer3.setAddress("广州市天河区");
        customer3.setStatus(Customer.CustomerStatus.ACTIVE);
        customerRepository.save(customer3);
        
        log.info("创建了3个测试客户");
    }
    
    private void createProducts() {
        Product product1 = new Product();
        product1.setName("iPhone 15 Pro");
        product1.setDescription("苹果最新旗舰手机，搭载A17 Pro芯片");
        product1.setPrice(new BigDecimal("7999.00"));
        product1.setSku("IPHONE15PRO-256GB");
        product1.setStockQuantity(50);
        product1.setCategory("手机");
        product1.setStatus(Product.ProductStatus.ACTIVE);
        productRepository.save(product1);
        
        Product product2 = new Product();
        product2.setName("MacBook Pro 14英寸");
        product2.setDescription("苹果专业级笔记本电脑，M3 Pro芯片");
        product2.setPrice(new BigDecimal("15999.00"));
        product2.setSku("MBP14-M3PRO-512GB");
        product2.setStockQuantity(20);
        product2.setCategory("电脑");
        product2.setStatus(Product.ProductStatus.ACTIVE);
        productRepository.save(product2);
        
        Product product3 = new Product();
        product3.setName("AirPods Pro 2");
        product3.setDescription("苹果主动降噪无线耳机");
        product3.setPrice(new BigDecimal("1899.00"));
        product3.setSku("AIRPODS-PRO-2");
        product3.setStockQuantity(100);
        product3.setCategory("耳机");
        product3.setStatus(Product.ProductStatus.ACTIVE);
        productRepository.save(product3);
        
        Product product4 = new Product();
        product4.setName("iPad Air 5");
        product4.setDescription("苹果平板电脑，M1芯片");
        product4.setPrice(new BigDecimal("4399.00"));
        product4.setSku("IPAD-AIR5-64GB");
        product4.setStockQuantity(30);
        product4.setCategory("平板");
        product4.setStatus(Product.ProductStatus.ACTIVE);
        productRepository.save(product4);
        
        Product product5 = new Product();
        product5.setName("Apple Watch Series 9");
        product5.setDescription("苹果智能手表，健康监测");
        product5.setPrice(new BigDecimal("2999.00"));
        product5.setSku("AW-S9-45MM");
        product5.setStockQuantity(0);
        product5.setCategory("手表");
        product5.setStatus(Product.ProductStatus.OUT_OF_STOCK);
        productRepository.save(product5);
        
        log.info("创建了5个测试商品");
    }
}
