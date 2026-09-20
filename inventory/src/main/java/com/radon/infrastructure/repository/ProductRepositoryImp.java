package com.radon.infrastructure.repository;

import com.radon.application.port.out.ProductRepository;
import com.radon.domain.Product;
import com.radon.exception.types.ProductNotFound;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImp(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product getProductById(Long id) {

        ProductEntity productEntity = productJpaRepository.findById(id).orElseThrow(() ->
                new ProductNotFound(id.toString())
        );

        return Product.of(productEntity);

    }
}
