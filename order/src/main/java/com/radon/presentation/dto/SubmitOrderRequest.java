package com.radon.presentation.dto;

import com.radon.exception.types.IllegalArgumentException;

import java.util.UUID;

public record SubmitOrderRequest(
        UUID cartId,
        String address
) {
    public SubmitOrderRequest{
        if (cartId == null) {
            throw new IllegalArgumentException("Cart id must not be null!");
        }
        if (address == null) {
            throw new IllegalArgumentException("Need address for submit order!");
        }
    }
}
