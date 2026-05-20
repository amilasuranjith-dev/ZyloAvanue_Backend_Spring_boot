package com.zyloavenue.api.feature.catalog.product.repository;

import com.zyloavenue.api.feature.catalog.product.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    Optional<ProductVariantEntity> findBySku(String sku);
    List<ProductVariantEntity> findByProductIdAndDeletedAtIsNull(Long productId);
    List<ProductVariantEntity> findByProductIdAndIsActiveAndDeletedAtIsNull(Long productId, boolean isActive);
    List<ProductVariantEntity> findByProductIdAndStockQtyLessThanAndDeletedAtIsNull(Long productId, Integer threshold);
}

