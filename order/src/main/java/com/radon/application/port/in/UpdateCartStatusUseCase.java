package com.radon.application.port.in;

import com.radon.domain.CartStatus;

import java.util.UUID;

public interface UpdateCartStatusUseCase {
    void updateCartStatus(UUID cartId, CartStatus cartStatus);
}
