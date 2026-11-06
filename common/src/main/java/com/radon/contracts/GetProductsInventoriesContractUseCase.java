package com.radon.contracts;

import java.util.List;

public interface GetProductsInventoriesContractUseCase {
    List<ProductInventoryContract> getProductsInventories(List<Long> productIds);
}
