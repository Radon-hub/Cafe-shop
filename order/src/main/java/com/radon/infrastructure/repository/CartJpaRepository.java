package com.radon.infrastructure.repository;

import com.radon.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUser_Id(UUID userId);
}
