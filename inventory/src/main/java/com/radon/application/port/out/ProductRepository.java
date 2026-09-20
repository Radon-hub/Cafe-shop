package com.radon.application.port.out;

import com.radon.domain.Product;


public interface ProductRepository {
    Product getProductById(Long id);
}
