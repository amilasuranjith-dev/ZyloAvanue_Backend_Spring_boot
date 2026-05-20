package com.zyloavenue.api.feature.catalog.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_variants", indexes = {
        @Index(name = "idx_variants_product_active", columnList = "product_id,is_active"),
        @Index(name = "idx_variants_stock", columnList = "stock_qty")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    @Column(name = "price_cents", nullable = false)
    private Integer priceCents;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQty;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == false && deletedAt == null) {
            isActive = true;
        }
        if (lowStockThreshold == null) {
            lowStockThreshold = 5;
        }
        if (stockQty == null) {
            stockQty = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

