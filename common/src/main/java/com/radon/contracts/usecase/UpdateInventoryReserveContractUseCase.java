package com.radon.contracts.usecase;

import com.radon.OperationResult;
import com.radon.contracts.dto.ReservationOperator;

import java.util.Map;

public interface UpdateInventoryReserveContractUseCase {
    OperationResult updateInventoryReserveContractUseCase(Map<Long,Integer> inventoriesAndReservedCounts, ReservationOperator operator);
}
