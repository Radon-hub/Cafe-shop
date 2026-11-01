package com.radon.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderItem(
    UUID id,
    Product product,
    int quantity,
    BigDecimal price,
    BigDecimal totalPrice,
    String productName,
    String productDescription
) {
}
