package com.zyloavenue.api.feature.payment.service;

import com.zyloavenue.api.common.exception.BadRequestException;
import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.payment.dto.PaymentCreateRequest;
import com.zyloavenue.api.feature.payment.dto.PaymentDto;
import com.zyloavenue.api.feature.payment.dto.PaymentResponseDto;
import com.zyloavenue.api.feature.payment.entity.PaymentEntity;
import com.zyloavenue.api.feature.payment.mapper.PaymentMapper;
import com.zyloavenue.api.feature.payment.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    /**
     * Initialize a payment (create payment record)
     */
    public PaymentDto initializePayment(PaymentCreateRequest request) {
        // Check for idempotency
        if (request.getIdempotencyKey() != null) {
            paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                    .ifPresent(existing -> {
                        throw new BadRequestException("Payment with this idempotency key already exists");
                    });
        }

        PaymentEntity payment = PaymentEntity.builder()
                .orderId(request.getOrderId())
                .provider(request.getProvider())
                .amountCents(request.getAmountCents())
                .currency(request.getCurrency() != null ? request.getCurrency() : "INR")
                .method(request.getMethod())
                .idempotencyKey(request.getIdempotencyKey())
                .status("PENDING")
                .build();

        PaymentEntity saved = paymentRepository.save(payment);
        return paymentMapper.toDto(saved);
    }

    /**
     * Get payment by ID
     */
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
        return paymentMapper.toDto(payment);
    }

    /**
     * Get payments for an order
     */
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get payments for an order (paginated)
     */
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentsByOrderPaginated(Long orderId, Pageable pageable) {
        return paymentRepository.findByOrderId(orderId, pageable)
                .map(paymentMapper::toResponseDto);
    }

    /**
     * Get payments by status (paginated)
     */
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentsByStatus(String status, Pageable pageable) {
        return paymentRepository.findByStatus(status, pageable)
                .map(paymentMapper::toResponseDto);
    }

    /**
     * Update payment status (for payment gateway callbacks)
     */
    public PaymentDto updatePaymentStatus(Long id, String newStatus) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));

        payment.setStatus(newStatus);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    /**
     * Mark payment as successful with gateway response
     */
    public PaymentDto markPaymentSuccessful(Long id, String gatewayTransactionRef, String gatewayPaymentId, String rawResponse) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));

        payment.setStatus("SUCCESS");
        payment.setGatewayTransactionRef(gatewayTransactionRef);
        payment.setGatewayPaymentId(gatewayPaymentId);
        payment.setGatewayRawResponse(rawResponse);

        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    /**
     * Mark payment as failed with error details
     */
    public PaymentDto markPaymentFailed(Long id, String failureCode, String failureMessage) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));

        payment.setStatus("FAILED");
        payment.setFailureCode(failureCode);
        payment.setFailureMessage(failureMessage);

        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }
}

