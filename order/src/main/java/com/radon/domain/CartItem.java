package com.radon.domain;

import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CartItem(
        Long id,
        Product product,
        int quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CartItem of(CartItemEntity cartItemEntity) {
        return CartItem.builder()
                .id(cartItemEntity.getId())
                .product(Product.of(cartItemEntity.getProduct()))
                .quantity(cartItemEntity.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
