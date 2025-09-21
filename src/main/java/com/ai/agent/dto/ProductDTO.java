package com.ai.agent.dto;

import com.ai.agent.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "商品名称不能为空")
    private String name;
    
    private String description;
    
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "商品价格必须大于0")
    private BigDecimal price;
    
    private String sku;
    
    @Min(value = 0, message = "库存数量不能为负数")
    private Integer stockQuantity;
    
    private Product.ProductStatus status;
    
    private String category;
    
    private BigDecimal originalPrice;
    
    private BigDecimal rating;
    
    private Integer reviewCount;
    
    private String imageUrl;
    
    private String tags;
    
    private Boolean isNew;
    
    private Boolean isBestseller;
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Product.ProductStatus getStatus() {
        return status;
    }
    
    public void setStatus(Product.ProductStatus status) {
        this.status = status;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
    
    public BigDecimal getRating() {
        return rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    public Integer getReviewCount() {
        return reviewCount;
    }
    
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public Boolean getIsNew() {
        return isNew;
    }
    
    public void setIsNew(Boolean aNew) {
        isNew = aNew;
    }
    
    public Boolean getIsBestseller() {
        return isBestseller;
    }
    
    public void setIsBestseller(Boolean bestseller) {
        isBestseller = bestseller;
    }
    
    // 转换为实体
    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setOriginalPrice(this.originalPrice);
        product.setRating(this.rating);
        product.setReviewCount(this.reviewCount != null ? this.reviewCount : 0);
        product.setImageUrl(this.imageUrl);
        product.setTags(this.tags);
        product.setIsNew(this.isNew != null ? this.isNew : false);
        product.setIsBestseller(this.isBestseller != null ? this.isBestseller : false);
        product.setSku(this.sku);
        product.setStockQuantity(this.stockQuantity != null ? this.stockQuantity : 0);
        product.setStatus(this.status != null ? this.status : Product.ProductStatus.ACTIVE);
        product.setCategory(this.category);
        return product;
    }
    
    // 从实体转换
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setImageUrl(product.getImageUrl());
        dto.setTags(product.getTags());
        dto.setIsNew(product.getIsNew());
        dto.setIsBestseller(product.getIsBestseller());
        dto.setSku(product.getSku());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setStatus(product.getStatus());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
