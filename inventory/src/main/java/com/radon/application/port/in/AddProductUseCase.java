package com.radon.application.port.in;

import com.radon.domain.Product;

public interface AddProductUseCase {
    Long addNewProduct(Product product);
}
