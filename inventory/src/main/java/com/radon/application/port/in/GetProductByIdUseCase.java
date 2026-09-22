package com.radon.application.port.in;

import com.radon.domain.Product;

public interface GetProductByIdUseCase {
    Product getProductById(Long id);
}
