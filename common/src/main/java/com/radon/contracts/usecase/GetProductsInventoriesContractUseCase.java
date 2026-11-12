package com.radon.contracts.usecase;

import com.radon.contracts.dto.ProductInventoryContract;

import java.util.List;

public interface GetProductsInventoriesContractUseCase {
    List<ProductInventoryContract> getProductsInventories(List<Long> productIds);
}
