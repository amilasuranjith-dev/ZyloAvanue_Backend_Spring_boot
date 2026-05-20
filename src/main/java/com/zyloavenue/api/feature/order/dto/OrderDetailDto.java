package com.zyloavenue.api.feature.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String customerEmail;
    private String customerName;
    private String customerPhone;
    private String shippingAddress1;
    private String shippingCity;
    private String shippingState;
    private String shippingPostal;
    private String shippingCountry;
    private String status;
    private String paymentStatus;
    private Integer subtotalCents;
    private Integer shippingCents;
    private Integer totalCents;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public double getSubtotalUSD() {
        return subtotalCents / 100.0;
    }

    public double getShippingUSD() {
        return shippingCents / 100.0;
    }

    public double getTotalUSD() {
        return totalCents / 100.0;
    }
}

