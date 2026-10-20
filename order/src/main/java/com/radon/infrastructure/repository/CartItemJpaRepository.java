package com.radon.infrastructure.repository;

import com.radon.infrastructure.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
}
