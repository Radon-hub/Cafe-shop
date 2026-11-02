package com.radon.presentation.dto;

import java.util.UUID;

public record SubmitOrderRequest(
        UUID cartId,
        String address
) {
}
