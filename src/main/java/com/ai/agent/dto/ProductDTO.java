package com.ai.agent.dto;

import com.ai.agent.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
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
    
    // 转换为实体
    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
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
        dto.setSku(product.getSku());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setStatus(product.getStatus());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
