package com.radon.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderItem(
    UUID id,
    Long productId,
    int quantity,
    BigDecimal price
) {
}
