package com.radon.application.port.in;

import com.radon.domain.Inventory;

public interface UpdateInventoryUseCase {
    Inventory updateInventory(Inventory inventory);
}
