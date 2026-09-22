package com.radon.domain;

import com.radon.infrastructure.entity.ProductEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Product(
        Long id,
        String name,
        String description,
        Category category,
        BigDecimal weight,
        BigDecimal price
) {

    public static Product of(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .category(Category.of(productEntity.getCategory()))
                .weight(productEntity.getWeight())
                .price(productEntity.getPrice())
                .build();
    }

}
