package com.radon.domain;

import com.radon.contracts.ProductContract;
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
                                        ProductContract.builder()
                                                .id(item.getProductId())
                                                .build()
                                )
                                .quantity(item.getQuantity())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
