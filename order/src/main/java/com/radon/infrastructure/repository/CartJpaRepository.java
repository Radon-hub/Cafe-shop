package com.radon.infrastructure.repository;

import com.radon.domain.CartStatus;
import com.radon.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByCustomerIdAndStatusIn(UUID customerId, Collection<CartStatus> statuses);

    @Modifying
    @Query("UPDATE CartEntity c SET c.status = :status WHERE c.id = :cartId")
    void updateCartStatus(UUID cartId,CartStatus status);

    Optional<CartEntity> findByCustomerId(UUID customerId);
}
