package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.contracts.dto.ReservationStatus;
import com.radon.domain.Reserved;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReservedRepository {
    OperationResult releaseReserveItemsByOrderIds(List<UUID> orderIds);
    List<Reserved> findAllByStatusAndExpiresAtLessThanEqual(ReservationStatus status, Instant expiresAtIsLessThan);
    List<Reserved> reserveProductsForOrder(Map<UUID, Map<Long, Integer>> orderWithProductsAndCounts);
}
