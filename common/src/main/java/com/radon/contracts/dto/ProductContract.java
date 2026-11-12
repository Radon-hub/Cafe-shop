package com.radon.contracts.dto;

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
    public static ProductContract from(ProductInventoryContract product) {
        return new ProductContract(
                product.id(),
                product.name(),
                product.description(),
                product.categoryName(),
                product.inventory().id(),
                product.weight(),
                product.price()
        );
    }
}
