package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.infrastructure.entity.UserEntity;

import java.util.UUID;

public interface CreateCartUseCase {
    OperationResult createCart(UUID customerId);
}
