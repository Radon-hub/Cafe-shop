package com.radon.contracts.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductInventoryContract(
        Long id,
        String name,
        String description,
        String categoryName,
        InventoryContract inventory,
        BigDecimal weight,
        BigDecimal price
) {
}
