package com.zyloavenue.api.feature.catalog.product.controller;

import com.zyloavenue.api.common.api.ApiResponse;
import com.zyloavenue.api.feature.catalog.product.dto.ProductDetailDto;
import com.zyloavenue.api.feature.catalog.product.dto.ProductSummaryDto;
import com.zyloavenue.api.feature.catalog.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * List all active products (paginated)
     * Public endpoint
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductSummaryDto>>> listActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductSummaryDto> products = productService.listActive(pageable);
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /**
     * Get product by ID
     * Public endpoint
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getById(@PathVariable Long id) {
        ProductDetailDto product = productService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    /**
     * Get product by slug
     * Public endpoint
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getBySlug(@PathVariable String slug) {
        ProductDetailDto product = productService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    /**
     * Create a new product
     * Only accessible to ADMIN
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDetailDto>> create(@Valid @RequestBody ProductDetailDto dto) {
        ProductDetailDto created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(created));
    }

    /**
     * Update a product
     * Only accessible to ADMIN
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDetailDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDetailDto dto) {
        ProductDetailDto updated = productService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.ok(updated));
    }

    /**
     * Delete a product (soft delete)
     * Only accessible to ADMIN
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}

