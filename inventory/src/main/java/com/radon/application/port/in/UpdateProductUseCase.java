package com.radon.application.port.in;

import com.radon.domain.Product;

public interface UpdateProductUseCase {
    Product updateProduct(Product product);
}
