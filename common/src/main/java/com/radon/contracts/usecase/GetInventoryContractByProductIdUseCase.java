package com.radon.contracts.usecase;

import com.radon.contracts.dto.InventoryContract;

public interface GetInventoryContractByProductIdUseCase {
    InventoryContract getInventoryContractByProductId(Long productId);
}
