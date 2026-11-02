package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.contracts.InventoryContract;
import com.radon.domain.Inventory;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.entity.ProductEntity;

import java.util.List;

public interface InventoryRepository {
    Inventory getInventoryByProductId(Long productId);
    List<Inventory> getInventoriesByProductIds(List<Long> productIds);
    OperationResult updateInventoryWarehouse(Inventory inventory);
    OperationResult updateInventoryCount(Inventory inventory);
    InventoryEntity addInventory(ProductEntity product, Inventory inventory);
    OperationResult deleteInventory(ProductEntity product,Inventory inventory);
}
