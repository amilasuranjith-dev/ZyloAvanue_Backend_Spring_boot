package com.zyloavenue.api.feature.payment.dto;

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
public class PaymentCreateRequest {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Amount is required")
    private Integer amountCents;

    private String provider;
    private String method;
    private String currency;
    private String idempotencyKey;
    private String gatewayTransactionRef;
}

