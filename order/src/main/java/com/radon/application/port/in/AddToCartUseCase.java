package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.domain.Product;

public interface AddToCartUseCase {
    OperationResult addToCart(Product product);
}
