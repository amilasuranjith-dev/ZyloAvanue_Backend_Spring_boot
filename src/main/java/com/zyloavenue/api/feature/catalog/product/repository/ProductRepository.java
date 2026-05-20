package com.zyloavenue.api.feature.catalog.product.repository;

import com.zyloavenue.api.feature.catalog.product.entity.ProductEntity;
import com.zyloavenue.api.feature.catalog.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findBySlug(String slug);
    Page<ProductEntity> findByStatus(ProductStatus status, Pageable pageable);
    Page<ProductEntity> findByStatusAndDeletedAtIsNull(ProductStatus status, Pageable pageable);
}

