package com.radon.infrastructure.repository;

import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByProductIdAndCart_Id(Long productId, UUID cartId);

    List<CartItemEntity> findAllByCart(CartEntity cart);
}
