package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    @Query("SELECT product FROM ProductEntity product WHERE product.name LIKE :name AND product.category.id = :categoryId AND product.price = :price AND product.weight = :weight")
    Optional<ProductEntity> isProductExists(String name, Long categoryId, BigDecimal price, BigDecimal weight);

    Page<ProductEntity> findAll(Specification<ProductEntity> specification, Pageable pageable);

}
