package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT product FROM ProductEntity product WHERE product.name LIKE :name AND product.category.id = :categoryId AND product.price = :price AND product.weight = :weight")
    Optional<ProductEntity> isProductExists(String name, Long categoryId, BigDecimal price, BigDecimal weight);
}
