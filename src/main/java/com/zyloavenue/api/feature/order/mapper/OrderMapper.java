package com.zyloavenue.api.feature.order.mapper;

import com.zyloavenue.api.feature.order.dto.OrderDetailDto;
import com.zyloavenue.api.feature.order.dto.OrderItemDto;
import com.zyloavenue.api.feature.order.dto.OrderResponseDto;
import com.zyloavenue.api.feature.order.entity.OrderEntity;
import com.zyloavenue.api.feature.order.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderItemDto toItemDto(OrderItemEntity entity) {
        if (entity == null) return null;

        return OrderItemDto.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .productId(entity.getProductId())
                .variantId(entity.getVariantId())
                .productNameSnapshot(entity.getProductNameSnapshot())
                .skuSnapshot(entity.getSkuSnapshot())
                .sizeSnapshot(entity.getSizeSnapshot())
                .colorSnapshot(entity.getColorSnapshot())
                .unitPriceCents(entity.getUnitPriceCents())
                .qty(entity.getQty())
                .lineTotalCents(entity.getLineTotalCents())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public OrderResponseDto toResponseDto(OrderEntity entity) {
        if (entity == null) return null;

        return OrderResponseDto.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .userId(entity.getUserId())
                .customerEmail(entity.getCustomerEmail())
                .customerName(entity.getCustomerName())
                .customerPhone(entity.getCustomerPhone())
                .shippingAddress1(entity.getShippingAddress1())
                .shippingCity(entity.getShippingCity())
                .shippingState(entity.getShippingState())
                .shippingPostal(entity.getShippingPostal())
                .shippingCountry(entity.getShippingCountry())
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .subtotalCents(entity.getSubtotalCents())
                .shippingCents(entity.getShippingCents())
                .totalCents(entity.getTotalCents())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public OrderDetailDto toDetailDto(OrderEntity entity, List<OrderItemEntity> items) {
        if (entity == null) return null;

        List<OrderItemDto> itemDtos = items == null ? List.of() :
                items.stream()
                        .map(this::toItemDto)
                        .collect(Collectors.toList());

        return OrderDetailDto.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .userId(entity.getUserId())
                .customerEmail(entity.getCustomerEmail())
                .customerName(entity.getCustomerName())
                .customerPhone(entity.getCustomerPhone())
                .shippingAddress1(entity.getShippingAddress1())
                .shippingCity(entity.getShippingCity())
                .shippingState(entity.getShippingState())
                .shippingPostal(entity.getShippingPostal())
                .shippingCountry(entity.getShippingCountry())
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .subtotalCents(entity.getSubtotalCents())
                .shippingCents(entity.getShippingCents())
                .totalCents(entity.getTotalCents())
                .items(itemDtos)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

