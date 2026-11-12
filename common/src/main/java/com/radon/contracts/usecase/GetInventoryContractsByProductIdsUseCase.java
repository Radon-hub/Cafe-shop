package com.radon.contracts.usecase;

import com.radon.contracts.dto.InventoryContract;

import java.util.List;

public interface GetInventoryContractsByProductIdsUseCase {
    List<InventoryContract> getInventoryContractByProductIds(List<Long> productIds);
}
