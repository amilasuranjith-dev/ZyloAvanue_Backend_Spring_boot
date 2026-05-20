package com.zyloavenue.api.feature.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovementCreateRequest {
    @NotNull(message = "Variant ID is required")
    private Long variantId;

    @NotNull(message = "Movement type is required")
    private String movementType;

    @NotNull(message = "Quantity delta is required")
    private Integer qtyDelta;

    private String reason;
    private Long referenceOrderId;
    private Long actorUserId;
}

