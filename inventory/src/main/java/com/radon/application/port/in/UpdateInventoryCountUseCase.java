package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.domain.Inventory;

public interface UpdateInventoryCountUseCase {
    OperationResult updateInventoryCount(Inventory inventory);
}
