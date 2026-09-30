package com.radon.presentation.dto;

import com.radon.domain.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        CategoryResponse category,
        InventoryResponse inventory,
        BigDecimal weight,
        BigDecimal price
) {
    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.id())
                .name(product.name())
                .description(product.description())
                .inventory(InventoryResponse.of(product.inventory()))
                .category(CategoryResponse.of(product.category()))
                .weight(product.weight())
                .price(product.price())
                .build();
    }
}
