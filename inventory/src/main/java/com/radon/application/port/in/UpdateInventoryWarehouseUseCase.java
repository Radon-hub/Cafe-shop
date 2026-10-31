package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.domain.Inventory;

public interface UpdateInventoryWarehouseUseCase {
    OperationResult updateInventoryWarehouse(Inventory inventory);
}
