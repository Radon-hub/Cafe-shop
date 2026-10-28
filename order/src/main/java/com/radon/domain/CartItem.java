package com.radon.domain;

import com.radon.contracts.ProductContract;
import com.radon.infrastructure.entity.CartItemEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CartItem(
        Long id,
        ProductContract product,
        int quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CartItem of(CartItemEntity cartItemEntity) {
        return CartItem.builder()
                .id(cartItemEntity.getId())
                .product(ProductContract.builder().id(cartItemEntity.getProductId()).build())
                .quantity(cartItemEntity.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
