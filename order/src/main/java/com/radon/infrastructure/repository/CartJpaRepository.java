package com.radon.infrastructure.repository;

import com.radon.domain.CartStatus;
import com.radon.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByCustomerIdAndStatusIn(UUID customerId, Collection<CartStatus> statuses);
}
