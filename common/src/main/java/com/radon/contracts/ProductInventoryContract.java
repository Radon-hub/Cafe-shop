package com.radon.contracts;

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
