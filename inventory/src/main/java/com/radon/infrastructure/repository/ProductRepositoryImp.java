package com.radon.infrastructure.repository;

import com.radon.application.port.out.ProductRepository;
import com.radon.domain.Product;
import com.radon.exception.types.ProductExistsException;
import com.radon.exception.types.ProductNotFoundException;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImp(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product getProductById(Long id) {

        ProductEntity productEntity = productJpaRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id.toString())
        );

        return Product.of(productEntity);

    }

    @Override
    public Long addNewProduct(Product product) {

        Optional<ProductEntity> existed = productJpaRepository.isProductExists(product.name(),product.category().id(),product.price(),product.weight());

        if(existed.isPresent()){
            throw new ProductExistsException(existed.get().getId());
        }

        ProductEntity productEntity = productJpaRepository.save(ProductEntity.of(product));
        return productEntity.getId();
    }


}
