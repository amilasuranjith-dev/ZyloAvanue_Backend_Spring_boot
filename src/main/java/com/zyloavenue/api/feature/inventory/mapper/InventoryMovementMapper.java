package com.zyloavenue.api.feature.inventory.mapper;

import com.zyloavenue.api.feature.inventory.dto.InventoryMovementDto;
import com.zyloavenue.api.feature.inventory.entity.InventoryMovementEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovementMapper {

    public InventoryMovementDto toDto(InventoryMovementEntity entity) {
        if (entity == null) return null;

        return InventoryMovementDto.builder()
                .id(entity.getId())
                .variantId(entity.getVariantId())
                .movementType(entity.getMovementType())
                .qtyDelta(entity.getQtyDelta())
                .reason(entity.getReason())
                .referenceOrderId(entity.getReferenceOrderId())
                .actorUserId(entity.getActorUserId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

