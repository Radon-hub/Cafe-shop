package com.radon.contracts.usecase;

import com.radon.OperationResult;

import java.util.List;
import java.util.UUID;

public interface ReleaseReserveItemsByIdsContractUseCase {
    OperationResult releaseReserveItemsByOrderIds(List<UUID> orderIds);
}
