package com.radon.infrastructure.jpa;

import com.radon.contracts.dto.ReservationStatus;
import com.radon.infrastructure.entity.ReservedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReservedJpaRepository extends JpaRepository<ReservedEntity, Long> {

    List<ReservedEntity> findAllByStatusAndExpiresAtLessThanEqual(ReservationStatus status, Instant expiresAtIsLessThan);

    @Modifying
    @Query("""
        UPDATE ReservedEntity res
            SET res.status = 'RELEASED'
               WHERE res.orderId IN :orderIds
               AND res.status = 'ACTIVE'
        """)
    int releaseByOrderIds(List<UUID> orderIds);
}
