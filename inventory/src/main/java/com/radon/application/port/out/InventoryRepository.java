package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.contracts.dto.ReservationOperator;
import com.radon.domain.Inventory;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.entity.ProductEntity;

import java.util.List;
import java.util.Map;

public interface InventoryRepository {
    Inventory getInventoryByProductId(Long productId);
    List<Inventory> getInventoriesByProductIds(List<Long> productIds);
    OperationResult updateInventoryWarehouse(Inventory inventory);
    OperationResult updateInventoryCount(Inventory inventory);
    OperationResult updateInventoryReserveCount(Map<Long,Integer> inventoriesAndReservedCounts, ReservationOperator operator);
    InventoryEntity addInventory(ProductEntity product, Inventory inventory);
    OperationResult deleteInventory(ProductEntity product,Inventory inventory);
}
