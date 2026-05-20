package com.zyloavenue.api.feature.payment.repository;

import com.zyloavenue.api.feature.payment.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByOrderId(Long orderId);
    Optional<PaymentEntity> findByIdempotencyKey(String idempotencyKey);
    Optional<PaymentEntity> findByGatewayTransactionRef(String gatewayTransactionRef);
    Page<PaymentEntity> findByOrderId(Long orderId, Pageable pageable);
    Page<PaymentEntity> findByStatus(String status, Pageable pageable);
}

