package com.zyloavenue.api.feature.order.service;

import com.zyloavenue.api.common.exception.BadRequestException;
import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.order.dto.OrderCreateRequest;
import com.zyloavenue.api.feature.order.dto.OrderDetailDto;
import com.zyloavenue.api.feature.order.dto.OrderResponseDto;
import com.zyloavenue.api.feature.order.entity.OrderEntity;
import com.zyloavenue.api.feature.order.entity.OrderItemEntity;
import com.zyloavenue.api.feature.order.mapper.OrderMapper;
import com.zyloavenue.api.feature.order.repository.OrderItemRepository;
import com.zyloavenue.api.feature.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    // Create a new order
    public OrderDetailDto createOrder(OrderCreateRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        // Calculate totals
        int subtotalCents = request.getItems().stream()
                .mapToInt(item -> item.getPriceCents() * item.getQty())
                .sum();

        int shippingCents = request.getShippingCents() != null ? request.getShippingCents() : 0;
        int totalCents = subtotalCents + shippingCents;

        // Create order entity
        OrderEntity order = OrderEntity.builder()
                .orderNumber(generateOrderNumber())
                .userId(request.getUserId())
                .customerEmail(request.getCustomerEmail())
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .shippingAddress1(request.getShippingAddress1())
                .shippingCity(request.getShippingCity())
                .shippingState(request.getShippingState())
                .shippingPostal(request.getShippingPostal())
                .shippingCountry(request.getShippingCountry() != null ? request.getShippingCountry() : "IN")
                .subtotalCents(subtotalCents)
                .shippingCents(shippingCents)
                .totalCents(totalCents)
                .status("PLACED")
                .paymentStatus("UNPAID")
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        // Create order items
        for (OrderCreateRequest.OrderItemCreateRequest itemRequest : request.getItems()) {
            OrderItemEntity item = OrderItemEntity.builder()
                    .orderId(savedOrder.getId())
                    .productId(itemRequest.getProductId())
                    .variantId(itemRequest.getVariantId())
                    .productNameSnapshot(itemRequest.getProductName())
                    .skuSnapshot(itemRequest.getSku())
                    .sizeSnapshot(itemRequest.getSize())
                    .colorSnapshot(itemRequest.getColor())
                    .unitPriceCents(itemRequest.getPriceCents())
                    .qty(itemRequest.getQty())
                    .lineTotalCents(itemRequest.getPriceCents() * itemRequest.getQty())
                    .build();

            orderItemRepository.save(item);
        }

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(savedOrder.getId());
        return orderMapper.toDetailDto(savedOrder, items);
    }

    //Get order by ID
    @Transactional(readOnly = true)
    public OrderDetailDto getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(id);
        return orderMapper.toDetailDto(order, items);
    }

    //Get order by order number
    @Transactional(readOnly = true)
    public OrderDetailDto getOrderByNumber(String orderNumber) {
        OrderEntity order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order not found with number: " + orderNumber));

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(order.getId());
        return orderMapper.toDetailDto(order, items);
    }

    //Get orders for a user (paginated)
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toResponseDto);
    }

    //Get orders by email (paginated)
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByEmail(String email, Pageable pageable) {
        return orderRepository.findByCustomerEmail(email, pageable)
                .map(orderMapper::toResponseDto);
    }

    //Get orders by status (paginated)
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(orderMapper::toResponseDto);
    }

    //Update order status
    public OrderDetailDto updateOrderStatus(Long id, String newStatus) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        order.setStatus(newStatus);
        orderRepository.save(order);

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(id);
        return orderMapper.toDetailDto(order, items);
    }

    //Update payment status
    public OrderDetailDto updatePaymentStatus(Long id, String newPaymentStatus) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        order.setPaymentStatus(newPaymentStatus);
        orderRepository.save(order);

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(id);
        return orderMapper.toDetailDto(order, items);
    }

    //Generate a unique order number
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

