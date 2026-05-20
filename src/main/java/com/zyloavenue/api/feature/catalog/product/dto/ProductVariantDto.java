package com.zyloavenue.api.feature.catalog.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDto {
    private Long id;
    private Long productId;
    private String sku;
    private String size;
    private String color;
    private Integer priceCents;
    private Integer stockQty;
    private boolean isActive;
    private Integer lowStockThreshold;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public double getPriceUSD() {
        return priceCents / 100.0;
    }
}

