package com.radon.application.port.in;

import com.radon.domain.Product;

import java.util.UUID;

public interface GetProductByIdUseCase {
    Product getProductById(Long id);
}
