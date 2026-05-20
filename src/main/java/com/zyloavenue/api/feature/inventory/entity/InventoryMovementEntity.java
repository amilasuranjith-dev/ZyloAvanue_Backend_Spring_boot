package com.zyloavenue.api.feature.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements", indexes = {
        @Index(name = "idx_inventory_movements_variant_created", columnList = "variant_id,created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @Column(name = "movement_type", nullable = false)
    private String movementType;

    @Column(name = "qty_delta", nullable = false)
    private Integer qtyDelta;

    @Column(name = "reason")
    private String reason;

    @Column(name = "reference_order_id")
    private Long referenceOrderId;

    @Column(name = "actor_user_id")
    private Long actorUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

