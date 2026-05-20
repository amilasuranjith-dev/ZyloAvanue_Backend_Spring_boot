package com.zyloavenue.api.feature.payment.controller;

import com.zyloavenue.api.common.api.ApiResponse;
import com.zyloavenue.api.feature.payment.dto.PaymentCreateRequest;
import com.zyloavenue.api.feature.payment.dto.PaymentDto;
import com.zyloavenue.api.feature.payment.dto.PaymentResponseDto;
import com.zyloavenue.api.feature.payment.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Initialize a payment
     * Only accessible to authenticated users
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PaymentDto>> initializePayment(@Valid @RequestBody PaymentCreateRequest request) {
        PaymentDto payment = paymentService.initializePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(payment));
    }

    /**
     * Get payment by ID
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<PaymentDto>> getPaymentById(@PathVariable Long id) {
        PaymentDto payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }

    /**
     * Get all payments for an order
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getPaymentsByOrder(@PathVariable Long orderId) {
        List<PaymentDto> payments = paymentService.getPaymentsByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.ok(payments));
    }

    /**
     * Get payments for an order (paginated)
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/order/{orderId}/paginated")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Page<PaymentResponseDto>>> getPaymentsByOrderPaginated(
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentResponseDto> payments = paymentService.getPaymentsByOrderPaginated(orderId, pageable);
        return ResponseEntity.ok(ApiResponse.ok(payments));
    }

    /**
     * Get payments by status (paginated)
     * Only accessible to ADMIN or STAFF
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Page<PaymentResponseDto>>> getPaymentsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentResponseDto> payments = paymentService.getPaymentsByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.ok(payments));
    }

    /**
     * Update payment status
     * Only accessible to ADMIN or STAFF
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<PaymentDto>> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        PaymentDto payment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }

    /**
     * Mark payment as successful (gateway callback endpoint)
     * Secured endpoint - should be called from payment gateway with signature verification
     */
    @PostMapping("/{id}/success")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<PaymentDto>> markPaymentSuccessful(
            @PathVariable Long id,
            @RequestParam String gatewayTransactionRef,
            @RequestParam(required = false) String gatewayPaymentId,
            @RequestParam(required = false) String rawResponse) {
        PaymentDto payment = paymentService.markPaymentSuccessful(id, gatewayTransactionRef, gatewayPaymentId, rawResponse);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }

    /**
     * Mark payment as failed (gateway callback endpoint)
     * Secured endpoint - should be called from payment gateway with signature verification
     */
    @PostMapping("/{id}/failure")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<PaymentDto>> markPaymentFailed(
            @PathVariable Long id,
            @RequestParam(required = false) String failureCode,
            @RequestParam(required = false) String failureMessage) {
        PaymentDto payment = paymentService.markPaymentFailed(id, failureCode, failureMessage);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }
}

