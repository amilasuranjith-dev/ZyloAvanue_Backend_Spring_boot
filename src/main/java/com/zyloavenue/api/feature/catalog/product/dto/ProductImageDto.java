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
public class ProductImageDto {
    private Long id;
    private Long productId;
    private String url;
    private String storageKey;
    private Integer sortOrder;
    private boolean isPrimary;
    private LocalDateTime createdAt;
}

