package com.zyloavenue.api.feature.catalog.product.mapper;

import com.zyloavenue.api.feature.catalog.product.dto.ProductDetailDto;
import com.zyloavenue.api.feature.catalog.product.dto.ProductImageDto;
import com.zyloavenue.api.feature.catalog.product.dto.ProductSummaryDto;
import com.zyloavenue.api.feature.catalog.product.dto.ProductVariantDto;
import com.zyloavenue.api.feature.catalog.product.entity.ProductEntity;
import com.zyloavenue.api.feature.catalog.product.entity.ProductImageEntity;
import com.zyloavenue.api.feature.catalog.product.entity.ProductVariantEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductImageDto toImageDto(ProductImageEntity entity) {
        if (entity == null) return null;

        return ProductImageDto.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .url(entity.getUrl())
                .storageKey(entity.getStorageKey())
                .sortOrder(entity.getSortOrder())
                .isPrimary(entity.isPrimary())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ProductVariantDto toVariantDto(ProductVariantEntity entity) {
        if (entity == null) return null;

        return ProductVariantDto.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .sku(entity.getSku())
                .size(entity.getSize())
                .color(entity.getColor())
                .priceCents(entity.getPriceCents())
                .stockQty(entity.getStockQty())
                .isActive(entity.isActive())
                .lowStockThreshold(entity.getLowStockThreshold())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProductSummaryDto toSummaryDto(ProductEntity entity, ProductImageEntity primaryImage) {
        if (entity == null) return null;

        return ProductSummaryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .primaryImage(toImageDto(primaryImage))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ProductDetailDto toDetailDto(ProductEntity entity, List<ProductImageEntity> images, List<ProductVariantEntity> variants) {
        if (entity == null) return null;

        List<ProductImageDto> imageDtos = images == null ? List.of() :
                images.stream()
                        .map(this::toImageDto)
                        .collect(Collectors.toList());

        List<ProductVariantDto> variantDtos = variants == null ? List.of() :
                variants.stream()
                        .map(this::toVariantDto)
                        .collect(Collectors.toList());

        return ProductDetailDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .images(imageDtos)
                .variants(variantDtos)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

