package com.radon.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CartItem(
        Long id,
        Product product,
        int quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
