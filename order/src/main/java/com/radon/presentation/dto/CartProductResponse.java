package com.radon.presentation.dto;

import com.radon.contracts.ProductContract;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartProductResponse(
        Long id,
        String name,
        String description,
        String categoryName,
        BigDecimal weight,
        BigDecimal price
) {
    public static CartProductResponse of(ProductContract product) {
        return new CartProductResponse(
                product.id(),
                product.name(),
                product.description(),
                product.categoryName(),
                product.weight(),
                product.price()
        );
    }
}
