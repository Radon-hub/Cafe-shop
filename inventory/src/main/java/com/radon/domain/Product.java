package com.radon.domain;

import com.radon.infrastructure.entity.ProductEntity;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record Product(
        Long id,
        String name,
        String description,
        Category category,
        Float weight,
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
