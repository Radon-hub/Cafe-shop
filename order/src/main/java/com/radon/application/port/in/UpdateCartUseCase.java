package com.radon.application.port.in;

import com.radon.OperationResult;

import java.util.UUID;

public interface UpdateCartUseCase {
    OperationResult updateCart(UUID cartId);
}
