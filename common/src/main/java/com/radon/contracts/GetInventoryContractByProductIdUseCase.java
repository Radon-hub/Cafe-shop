package com.radon.contracts;

public interface GetInventoryContractByProductIdUseCase {
    InventoryContract getInventoryContractByProductId(Long productId);
}
