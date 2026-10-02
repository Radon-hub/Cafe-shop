package com.radon.presentation.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductUpdateRequest(
        String name,
        String description,
        Long categoryId,
        BigDecimal weight,
        BigDecimal price
) {
}
