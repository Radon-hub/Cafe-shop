package com.radon.domain;

import com.radon.infrastructure.entity.ProductEntity;
import com.radon.presentation.dto.ProductAddRequest;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Product(
        Long id,
        String name,
        String description,
        Category category,
        Inventory inventory,
        BigDecimal weight,
        BigDecimal price
) {

    public static Product of(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .inventory(Inventory.of(productEntity.getInventory()))
                .category(Category.of(productEntity.getCategory()))
                .weight(productEntity.getWeight())
                .price(productEntity.getPrice())
                .build();
    }
    public static Product of(ProductAddRequest req) {
        return Product.builder()
                .name(req.name())
                .description(req.description())
                .inventory(Inventory.builder().wareHouse(Warehouse.builder().id(req.wareHouseId()).build()).build())
                .category(Category.builder().id(req.categoryId()).build())
                .weight(req.weight())
                .price(req.price())
                .build();
    }

}
