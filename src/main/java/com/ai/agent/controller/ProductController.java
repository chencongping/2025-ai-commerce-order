package com.ai.agent.controller;

import com.ai.agent.common.ApiResponse;
import com.ai.agent.dto.ProductDTO;
import com.ai.agent.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "商品管理", description = "商品相关API")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping
    @Operation(summary = "创建商品", description = "创建新商品")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO.getName());
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("商品创建成功", createdProduct));
    }
    
    @GetMapping
    @Operation(summary = "获取所有商品", description = "获取所有商品列表")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        log.info("Fetching all products");
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("获取商品列表成功", products));
    }
    
    @GetMapping("/available")
    @Operation(summary = "获取可用商品", description = "获取有库存的商品列表")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAvailableProducts() {
        log.info("Fetching available products");
        List<ProductDTO> products = productService.getAvailableProducts();
        return ResponseEntity.ok(ApiResponse.success("获取可用商品成功", products));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取商品", description = "根据商品ID获取商品详情")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(
            @Parameter(description = "商品ID") @PathVariable Long id) {
        log.info("Fetching product by ID: {}", id);
        Optional<ProductDTO> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取商品成功", product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "商品不存在"));
        }
    }
    
    @GetMapping("/sku/{sku}")
    @Operation(summary = "根据SKU获取商品", description = "根据商品SKU获取商品信息")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductBySku(
            @Parameter(description = "商品SKU") @PathVariable String sku) {
        log.info("Fetching product by SKU: {}", sku);
        Optional<ProductDTO> product = productService.getProductBySku(sku);
        if (product.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("获取商品成功", product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("404", "商品不存在"));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索商品", description = "根据关键词搜索商品")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> searchProducts(
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        log.info("Searching products with keyword: {}", keyword);
        List<ProductDTO> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(ApiResponse.success("搜索商品成功", products));
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "根据分类获取商品", description = "根据商品分类获取商品列表")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(
            @Parameter(description = "商品分类") @PathVariable String category) {
        log.info("Fetching products by category: {}", category);
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success("获取分类商品成功", products));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "更新商品信息")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        log.info("Updating product with ID: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(ApiResponse.success("商品更新成功", updatedProduct));
    }
    
    @PutMapping("/{id}/stock")
    @Operation(summary = "更新商品库存", description = "更新商品库存数量")
    public ResponseEntity<ApiResponse<ProductDTO>> updateStock(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @Parameter(description = "库存数量") @RequestParam Integer quantity) {
        log.info("Updating stock for product ID: {} with quantity: {}", id, quantity);
        ProductDTO updatedProduct = productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success("库存更新成功", updatedProduct));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品", description = "删除商品")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(
            @Parameter(description = "商品ID") @PathVariable Long id) {
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("商品删除成功", null));
    }
}
