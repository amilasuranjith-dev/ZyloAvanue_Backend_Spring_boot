package com.zyloavenue.api.feature.inventory.dto;

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
public class InventoryMovementDto {
    private Long id;
    private Long variantId;
    private String movementType;
    private Integer qtyDelta;
    private String reason;
    private Long referenceOrderId;
    private Long actorUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

