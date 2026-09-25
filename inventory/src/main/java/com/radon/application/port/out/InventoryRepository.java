package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.domain.Inventory;

public interface InventoryRepository {
    Inventory addInventory(Inventory inventory);
    OperationResult deleteInventory(Inventory inventory);
}
