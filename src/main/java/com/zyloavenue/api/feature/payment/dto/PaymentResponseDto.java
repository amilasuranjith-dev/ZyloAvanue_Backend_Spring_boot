package com.zyloavenue.api.feature.payment.dto;

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
public class PaymentResponseDto {
    private Long id;
    private Long orderId;
    private String status;
    private String provider;
    private Integer amountCents;
    private String currency;
    private String gatewayTransactionRef;
    private String gatewayPaymentId;
    private String method;
    private String failureCode;
    private String failureMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public double getAmountUSD() {
        return amountCents / 100.0;
    }
}

