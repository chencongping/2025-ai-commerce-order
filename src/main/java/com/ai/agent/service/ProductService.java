package com.ai.agent.service;

import com.ai.agent.dto.ProductDTO;
import com.ai.agent.entity.Product;
import com.ai.agent.repository.ProductRepository;
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
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO.getName());
        
        // 检查SKU是否已存在
        if (productDTO.getSku() != null && productRepository.findBySku(productDTO.getSku()).isPresent()) {
            throw new RuntimeException("SKU已存在: " + productDTO.getSku());
        }
        
        Product product = productDTO.toEntity();
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        
        return ProductDTO.fromEntity(savedProduct);
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll().stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long id) {
        log.info("Fetching product by ID: {}", id);
        return productRepository.findById(id)
                .map(ProductDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductBySku(String sku) {
        log.info("Fetching product by SKU: {}", sku);
        return productRepository.findBySku(sku)
                .map(ProductDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getAvailableProducts() {
        log.info("Fetching available products");
        return productRepository.findByStockQuantityGreaterThanAndStatus(0, Product.ProductStatus.ACTIVE).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword) {
        log.info("Searching products with keyword: {}", keyword);
        return productRepository.findByNameContainingOrDescriptionContainingOrCategoryContaining(keyword, keyword, keyword).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(String category) {
        log.info("Fetching products by category: {}", category);
        return productRepository.findByCategory(category).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在: " + id));
        
        // 检查SKU是否被其他商品使用
        if (productDTO.getSku() != null && !productDTO.getSku().equals(existingProduct.getSku())) {
            if (productRepository.findBySku(productDTO.getSku()).isPresent()) {
                throw new RuntimeException("SKU已被其他商品使用: " + productDTO.getSku());
            }
        }
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setSku(productDTO.getSku());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setStatus(productDTO.getStatus());
        existingProduct.setCategory(productDTO.getCategory());
        
        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully");
        
        return ProductDTO.fromEntity(updatedProduct);
    }
    
    public ProductDTO updateStock(Long id, Integer quantity) {
        log.info("Updating stock for product ID: {} with quantity: {}", id, quantity);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在: " + id));
        
        if (quantity < 0) {
            throw new RuntimeException("库存数量不能为负数");
        }
        
        product.setStockQuantity(quantity);
        
        // 如果库存为0，自动设置为缺货状态
        if (quantity == 0) {
            product.setStatus(Product.ProductStatus.OUT_OF_STOCK);
        } else if (product.getStatus() == Product.ProductStatus.OUT_OF_STOCK) {
            product.setStatus(Product.ProductStatus.ACTIVE);
        }
        
        Product updatedProduct = productRepository.save(product);
        log.info("Stock updated successfully");
        
        return ProductDTO.fromEntity(updatedProduct);
    }
    
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在: " + id));
        
        // 检查是否有订单项关联
        if (!product.getOrderItems().isEmpty()) {
            throw new RuntimeException("商品已被订单使用，无法删除");
        }
        
        productRepository.delete(product);
        log.info("Product deleted successfully");
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getNewProducts() {
        log.info("Fetching new products");
        return productRepository.findByIsNewTrueAndStatus(Product.ProductStatus.ACTIVE).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getBestsellerProducts() {
        log.info("Fetching bestseller products");
        return productRepository.findByIsBestsellerTrueAndStatus(Product.ProductStatus.ACTIVE).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductDTO> getTopRatedProducts(int limit) {
        log.info("Fetching top rated products with limit: {}", limit);
        return productRepository.findTop5ByStatusOrderByRatingDesc(Product.ProductStatus.ACTIVE).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
