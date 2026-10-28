package com.radon.contracts;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductContract(
        Long id,
        String name,
        String description,
        String categoryName,
        Long inventoryId,
        BigDecimal weight,
        BigDecimal price
) {
}
