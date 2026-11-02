package com.radon.contracts;

import java.util.List;

public interface GetInventoryContractsByProductIdsUseCase {
    List<InventoryContract> getInventoryContractByProductId(List<Long> productIds);
}
