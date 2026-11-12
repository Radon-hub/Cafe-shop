package com.radon.infrastructure.repository;

import com.radon.OperationResult;
import com.radon.application.port.out.ReservedRepository;
import com.radon.contracts.dto.ReservationStatus;
import com.radon.domain.Reserved;
import com.radon.infrastructure.entity.ReservedEntity;
import com.radon.infrastructure.jpa.ReservedJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ReservedRepositoryImp implements ReservedRepository {

    private final ReservedJpaRepository reservedJpaRepository;

    public ReservedRepositoryImp(ReservedJpaRepository reservedJpaRepository) {
        this.reservedJpaRepository = reservedJpaRepository;
    }

    @Override
    @Transactional
    public OperationResult releaseReserveItemsByOrderIds(List<UUID> orderIds) {
        reservedJpaRepository.releaseByOrderIds(orderIds);
        return OperationResult.SUCCESS;
    }

    @Override
    public List<Reserved> findAllByStatusAndExpiresAtLessThanEqual(ReservationStatus status, Instant expiresAtIsLessThan) {
        List<ReservedEntity> allByStatusAndExpiresAtLessThanEqual = reservedJpaRepository.findAllByStatusAndExpiresAtLessThanEqual(status, expiresAtIsLessThan);
        return allByStatusAndExpiresAtLessThanEqual.stream().map(Reserved::fromEntity).toList();
    }

    @Override
    @Transactional
    public List<Reserved> reserveProductsForOrder(Map<UUID, Map<Long, Integer>> orderWithProductsAndCounts) {

        log.info("ReserveProducts -> received products for reservation -> Products: {}", orderWithProductsAndCounts);
        List<ReservedEntity> reservedEntities = new ArrayList<>();

        Instant expirationTime = Instant.now().plus(30L, ChronoUnit.MINUTES);

        orderWithProductsAndCounts.forEach( (orderId,productIds) -> {
            productIds.forEach( (productId,count) -> {
                reservedEntities.add(
                        new ReservedEntity(
                                productId,
                                orderId,
                                count,
                                expirationTime,
                                ReservationStatus.ACTIVE
                        )
                );
            });
        });

        log.info("ReserveProducts -> list of entities created for reservation -> List Length: {} , Expiration Time: {}", orderWithProductsAndCounts.size(),expirationTime);

        log.info("ReserveProducts -> SUCCESS");

        return reservedJpaRepository.saveAll(reservedEntities).stream().map(Reserved::fromEntity).collect(Collectors.toList());

    }
}
