package com.radon.presentation.dto;

import com.radon.domain.CartItem;
import lombok.Builder;

@Builder
public record CartItemResponse(
        Long id,
        CartProductResponse product,
        int quantity
) {
    public static CartItemResponse of(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.id(),
                CartProductResponse.of(cartItem.product()),
                cartItem.quantity()
        );
    }
}
