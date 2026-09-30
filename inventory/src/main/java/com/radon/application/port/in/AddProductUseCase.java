package com.radon.application.port.in;

import com.radon.domain.Product;

public interface AddProductUseCase {
    Product addNewProduct(Product product);
}
