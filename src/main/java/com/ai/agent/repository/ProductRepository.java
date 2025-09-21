package com.ai.agent.repository;

import com.ai.agent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySku(String sku);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByStatus(Product.ProductStatus status);
    
    List<Product> findByCategory(String category);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 AND p.status = 'ACTIVE'")
    List<Product> findAvailableProducts();
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.category LIKE %:keyword%")
    List<Product> searchProducts(@Param("keyword") String keyword);
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}
