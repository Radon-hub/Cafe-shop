package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.domain.Product;

public interface RemoveFromCartUseCase {
    OperationResult removeFromCart(Product product);
}
