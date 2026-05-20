package com.zyloavenue.api.feature.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    @NotNull(message = "Customer email is required")
    private String customerEmail;

    private String customerName;
    private String customerPhone;
    private Long userId;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress1;

    @NotBlank(message = "City is required")
    private String shippingCity;

    @NotBlank(message = "State is required")
    private String shippingState;

    @NotBlank(message = "Postal code is required")
    private String shippingPostal;

    private String shippingCountry;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemCreateRequest> items;

    private Integer shippingCents;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemCreateRequest {
        @NotNull(message = "Variant ID is required")
        private Long variantId;

        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotBlank(message = "Product name is required")
        private String productName;

        @NotBlank(message = "SKU is required")
        private String sku;

        @NotBlank(message = "Size is required")
        private String size;

        @NotBlank(message = "Color is required")
        private String color;

        @NotNull(message = "Price is required")
        private Integer priceCents;

        @NotNull(message = "Quantity is required")
        private Integer qty;
    }
}

