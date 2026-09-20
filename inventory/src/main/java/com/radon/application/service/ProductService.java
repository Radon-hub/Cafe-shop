package com.radon.application.service;

import com.radon.application.port.in.GetProductByIdUseCase;
import com.radon.application.port.in.GetProductsByCategoryUseCase;
import com.radon.application.port.out.ProductRepository;
import com.radon.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements GetProductByIdUseCase , GetProductsByCategoryUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }
}
