package com.zyloavenue.api.feature.inventory.repository;

import com.zyloavenue.api.feature.inventory.entity.InventoryMovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovementEntity, Long> {
    List<InventoryMovementEntity> findByVariantId(Long variantId);
    Page<InventoryMovementEntity> findByVariantId(Long variantId, Pageable pageable);
    List<InventoryMovementEntity> findByReferenceOrderId(Long orderId);
}

