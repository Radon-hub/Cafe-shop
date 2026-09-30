package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.domain.Inventory;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.entity.ProductEntity;

public interface InventoryRepository {
    InventoryEntity addInventory(ProductEntity product, Inventory inventory);
    OperationResult deleteInventory(ProductEntity product,Inventory inventory);
}
