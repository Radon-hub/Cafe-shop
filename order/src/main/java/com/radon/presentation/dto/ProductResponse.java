package com.radon.presentation.dto;

import com.radon.domain.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        String categoryName,
        BigDecimal weight,
        BigDecimal price
) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.id(),
                product.name(),
                product.description(),
                product.category().name(),
                product.weight(),
                product.price()
        );
    }
}
