package com.radon.domain;

import com.radon.infrastructure.entity.CartEntity;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record Cart(
        UUID id,
        User user,
        List<CartItem> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Cart of(CartEntity cartEntity) {
        return Cart.builder()
                .id(cartEntity.getId())
                .items(cartEntity.getItems().stream().map( item ->
                        CartItem.builder()
                                .id(item.getId())
                                .product(
                                        Product.builder()
                                                .id(item.getProduct().getId())
                                                .name(item.getProduct().getName())
                                                .price(item.getProduct().getPrice())
                                                .weight(item.getProduct().getWeight())
                                                .category(Category.of(item.getProduct().getCategory()))
                                                .description(item.getProduct().getDescription())
                                                .build()
                                )
                                .quantity(item.getQuantity())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
