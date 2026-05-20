package com.zyloavenue.api.feature.catalog.product.dto;

import com.zyloavenue.api.feature.catalog.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private ProductStatus status;
    private List<ProductImageDto> images;
    private List<ProductVariantDto> variants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

