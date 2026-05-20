package com.zyloavenue.api.feature.inventory.controller;

import com.zyloavenue.api.common.api.ApiResponse;
import com.zyloavenue.api.feature.inventory.dto.InventoryMovementCreateRequest;
import com.zyloavenue.api.feature.inventory.dto.InventoryMovementDto;
import com.zyloavenue.api.feature.inventory.service.InventoryMovementService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-movements")
public class InventoryMovementController {

    private final InventoryMovementService inventoryMovementService;

    public InventoryMovementController(InventoryMovementService inventoryMovementService) {
        this.inventoryMovementService = inventoryMovementService;
    }

    /**
     * Record an inventory movement
     * Only accessible to ADMIN or STAFF
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<InventoryMovementDto>> recordMovement(
            @Valid @RequestBody InventoryMovementCreateRequest request) {
        InventoryMovementDto movement = inventoryMovementService.recordMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(movement));
    }

    /**
     * Get movements for a variant (paginated)
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/variant/{variantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Page<InventoryMovementDto>>> getMovementsByVariant(
            @PathVariable Long variantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InventoryMovementDto> movements = inventoryMovementService.getMovementsByVariant(variantId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(movements));
    }

    /**
     * Get movements for an order
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<InventoryMovementDto>>> getMovementsByOrder(
            @PathVariable Long orderId) {
        List<InventoryMovementDto> movements = inventoryMovementService.getMovementsByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.ok(movements));
    }

    /**
     * Get a specific movement
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<InventoryMovementDto>> getById(@PathVariable Long id) {
        InventoryMovementDto movement = inventoryMovementService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(movement));
    }
}

