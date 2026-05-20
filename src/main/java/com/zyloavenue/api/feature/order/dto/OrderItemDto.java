package com.zyloavenue.api.feature.order.dto;

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
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long variantId;
    private String productNameSnapshot;
    private String skuSnapshot;
    private String sizeSnapshot;
    private String colorSnapshot;
    private Integer unitPriceCents;
    private Integer qty;
    private Integer lineTotalCents;
    private LocalDateTime createdAt;

    public double getUnitPriceUSD() {
        return unitPriceCents / 100.0;
    }

    public double getLineTotalUSD() {
        return lineTotalCents / 100.0;
    }
}

