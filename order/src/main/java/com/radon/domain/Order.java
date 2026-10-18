package com.radon.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record Order(
        UUID id,
        String orderNumber,
        UUID userId,
        OrderStatus orderStatus,
        BigDecimal totalPrice,
        String paymentId,
        String idempotencyKey,
        List<OrderItem> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
