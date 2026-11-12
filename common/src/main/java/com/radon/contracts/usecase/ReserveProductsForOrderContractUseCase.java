package com.radon.contracts.usecase;

import com.radon.contracts.dto.ReservedContract;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReserveProductsForOrderContractUseCase {
    List<ReservedContract> reserveProductForOrder(Map<UUID,Map<Long,Integer>> orderWithProductsAndCounts);
}
