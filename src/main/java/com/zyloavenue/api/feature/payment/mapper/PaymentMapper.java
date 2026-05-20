package com.zyloavenue.api.feature.payment.mapper;

import com.zyloavenue.api.feature.payment.dto.PaymentDto;
import com.zyloavenue.api.feature.payment.dto.PaymentResponseDto;
import com.zyloavenue.api.feature.payment.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto toDto(PaymentEntity entity) {
        if (entity == null) return null;

        return PaymentDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .provider(entity.getProvider())
                .status(entity.getStatus())
                .amountCents(entity.getAmountCents())
                .currency(entity.getCurrency())
                .gatewayTransactionRef(entity.getGatewayTransactionRef())
                .gatewayPaymentId(entity.getGatewayPaymentId())
                .method(entity.getMethod())
                .failureCode(entity.getFailureCode())
                .failureMessage(entity.getFailureMessage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PaymentResponseDto toResponseDto(PaymentEntity entity) {
        if (entity == null) return null;

        return PaymentResponseDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .status(entity.getStatus())
                .provider(entity.getProvider())
                .amountCents(entity.getAmountCents())
                .currency(entity.getCurrency())
                .gatewayTransactionRef(entity.getGatewayTransactionRef())
                .gatewayPaymentId(entity.getGatewayPaymentId())
                .method(entity.getMethod())
                .failureCode(entity.getFailureCode())
                .failureMessage(entity.getFailureMessage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

