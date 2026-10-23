package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.infrastructure.entity.UserEntity;

public interface CreateCartUseCase {
    OperationResult createCart(UserEntity userEntity);
}
