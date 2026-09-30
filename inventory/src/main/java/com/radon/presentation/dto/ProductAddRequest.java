package com.radon.presentation.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductAddRequest(
        String name,
        String description,
        Long wareHouseId,
        Long categoryId,
        BigDecimal weight,
        BigDecimal price
) {
}
