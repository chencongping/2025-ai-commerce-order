package com.ai.agent.repository;

import com.ai.agent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 基本查询方法
    Optional<Product> findBySku(String sku);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByStatus(Product.ProductStatus status);
    
    List<Product> findByCategory(String category);
    
    // 高级查询方法
    List<Product> findByStockQuantityGreaterThanAndStatus(Integer stockQuantity, Product.ProductStatus status);
    
    List<Product> findByNameContainingOrDescriptionContainingOrCategoryContaining(String nameKeyword, String descriptionKeyword, String categoryKeyword);
    
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<Product> findByIsNewTrueAndStatus(Product.ProductStatus status);
    
    List<Product> findByIsBestsellerTrueAndStatus(Product.ProductStatus status);
    
    List<Product> findTop5ByStatusOrderByRatingDesc(Product.ProductStatus status);
}
