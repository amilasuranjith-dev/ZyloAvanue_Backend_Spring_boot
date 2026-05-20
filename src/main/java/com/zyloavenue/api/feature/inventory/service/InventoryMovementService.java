package com.zyloavenue.api.feature.inventory.service;

import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.inventory.dto.InventoryMovementCreateRequest;
import com.zyloavenue.api.feature.inventory.dto.InventoryMovementDto;
import com.zyloavenue.api.feature.inventory.entity.InventoryMovementEntity;
import com.zyloavenue.api.feature.inventory.mapper.InventoryMovementMapper;
import com.zyloavenue.api.feature.inventory.repository.InventoryMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    public InventoryMovementService(
            InventoryMovementRepository inventoryMovementRepository,
            InventoryMovementMapper inventoryMovementMapper) {
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.inventoryMovementMapper = inventoryMovementMapper;
    }

    /**
     * Record an inventory movement
     */
    public InventoryMovementDto recordMovement(InventoryMovementCreateRequest request) {
        InventoryMovementEntity entity = InventoryMovementEntity.builder()
                .variantId(request.getVariantId())
                .movementType(request.getMovementType())
                .qtyDelta(request.getQtyDelta())
                .reason(request.getReason())
                .referenceOrderId(request.getReferenceOrderId())
                .actorUserId(request.getActorUserId())
                .build();

        InventoryMovementEntity saved = inventoryMovementRepository.save(entity);
        return inventoryMovementMapper.toDto(saved);
    }

    /**
     * Get movements for a variant (paginated)
     */
    @Transactional(readOnly = true)
    public Page<InventoryMovementDto> getMovementsByVariant(Long variantId, Pageable pageable) {
        return inventoryMovementRepository.findByVariantId(variantId, pageable)
                .map(inventoryMovementMapper::toDto);
    }

    /**
     * Get movements for an order
     */
    @Transactional(readOnly = true)
    public List<InventoryMovementDto> getMovementsByOrder(Long orderId) {
        return inventoryMovementRepository.findByReferenceOrderId(orderId)
                .stream()
                .map(inventoryMovementMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific movement
     */
    @Transactional(readOnly = true)
    public InventoryMovementDto getById(Long id) {
        InventoryMovementEntity movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory movement not found with id: " + id));
        return inventoryMovementMapper.toDto(movement);
    }
}

