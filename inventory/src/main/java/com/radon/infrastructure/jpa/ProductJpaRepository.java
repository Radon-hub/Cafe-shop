package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    @Query("SELECT product FROM ProductEntity product WHERE product.name LIKE :name AND product.category.id = :categoryId AND product.price = :price AND product.weight = :weight")
    Optional<ProductEntity> isProductExists(String name, Long categoryId, BigDecimal price, BigDecimal weight);

    Page<ProductEntity> findAll(Specification<ProductEntity> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"category","inventory"})
    @Query("SELECT product FROM ProductEntity product WHERE product.id in :ids")
    List<ProductEntity> findByIds(@Param("ids") Collection<Long> ids);

//    @Query("SELECT p FROM ProductEntity p JOIN InventoryEntity i ON i.product.id = p.id JOIN CategoryEntity c ON c.id = p.category.id WHERE p.id = :id")
    @EntityGraph(attributePaths = {"category","inventory"})
    Optional<ProductEntity> findById(@Param("id") Long id);
}
