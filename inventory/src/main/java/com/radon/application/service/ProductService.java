package com.radon.application.service;

import com.radon.application.port.in.*;
import com.radon.application.port.out.ProductRepository;
import com.radon.domain.Product;
import com.radon.presentation.dto.ProductSearchFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements AddProductUseCase,GetProductByIdUseCase,GetProductsByCategoryUseCase,UpdateProductUseCase, SearchProductUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Product addNewProduct(Product product) {
        return productRepository.addNewProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    public Page<Product> searchProduct(ProductSearchFilterRequest filter, int page, int size) {
        return productRepository.searchProduct(filter, page, size);
    }
}
