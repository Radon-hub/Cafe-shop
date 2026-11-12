package com.radon.infrastructure.repository;

import com.radon.infrastructure.entity.OrderEntity;
import com.radon.infrastructure.entity.ReservedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    @Modifying
    @Query("""
        UPDATE OrderEntity order
            SET order.orderStatus = 'EXPIRED'
                WHERE order.id IN :orderIds
    """)
    int expireByOrderIds(List<UUID> orderIds);
}
