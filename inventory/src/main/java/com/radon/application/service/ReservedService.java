package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.out.ReservedRepository;
import com.radon.contracts.dto.ReservationStatus;
import com.radon.contracts.usecase.GetReservedItemsByTimeContractUseCase;
import com.radon.contracts.usecase.ReleaseReserveItemsByIdsContractUseCase;
import com.radon.contracts.usecase.ReserveProductsForOrderContractUseCase;
import com.radon.contracts.dto.ReservedContract;
import com.radon.domain.Reserved;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservedService implements ReleaseReserveItemsByIdsContractUseCase,ReserveProductsForOrderContractUseCase, GetReservedItemsByTimeContractUseCase {

    private final ReservedRepository reservedRepository;

    public ReservedService(ReservedRepository reservedRepository) {
        this.reservedRepository = reservedRepository;
    }

    @Override
    public List<ReservedContract> reserveProductForOrder(Map<UUID, Map<Long, Integer>> orderWithProductsAndCounts) {
        return reservedRepository.reserveProductsForOrder(orderWithProductsAndCounts).stream().map(Reserved::toContract).collect(Collectors.toList());
    }

    @Override
    public List<ReservedContract> getReservedItemsByTimeUseCase(ReservationStatus status, Instant expiresAtIsLessThan) {
        return reservedRepository.findAllByStatusAndExpiresAtLessThanEqual(status,expiresAtIsLessThan).stream().map(Reserved::toContract).collect(Collectors.toList());
    }

    @Override
    public OperationResult releaseReserveItemsByOrderIds(List<UUID> orderIds) {
        return reservedRepository.releaseReserveItemsByOrderIds(orderIds);
    }
}
