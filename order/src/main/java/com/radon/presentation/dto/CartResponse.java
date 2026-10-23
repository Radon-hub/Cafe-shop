package com.radon.presentation.dto;

import com.radon.domain.Cart;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CartResponse(
        UUID id,
        List<CartItemResponse> items
) {
    public static CartResponse of(Cart cart) {
        return new CartResponse(
                cart.id(),
                cart.items().stream().map(CartItemResponse::of).toList()
        );
    }
}
