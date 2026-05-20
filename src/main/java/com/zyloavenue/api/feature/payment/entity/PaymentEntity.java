package com.zyloavenue.api.feature.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payments_order", columnList = "order_id"),
        @Index(name = "idx_payments_status", columnList = "status"),
        @Index(name = "idx_payments_gateway_ref", columnList = "gateway_transaction_ref")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "provider")
    private String provider;

    @Column(nullable = false)
    private String status;

    @Column(name = "amount_cents", nullable = false)
    private Integer amountCents;

    @Column(nullable = false)
    private String currency;

    @Column(name = "gateway_transaction_ref")
    private String gatewayTransactionRef;

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "gateway_raw_response", columnDefinition = "JSON")
    private String gatewayRawResponse;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "method")
    private String method;

    @Column(name = "failure_code")
    private String failureCode;

    @Column(name = "failure_message")
    private String failureMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
        if (currency == null) currency = "INR";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

