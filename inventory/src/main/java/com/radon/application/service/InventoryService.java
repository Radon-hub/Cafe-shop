package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.UpdateInventoryCountUseCase;
import com.radon.application.port.in.UpdateInventoryWarehouseUseCase;
import com.radon.application.port.out.InventoryRepository;
import com.radon.domain.Inventory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService implements UpdateInventoryCountUseCase, UpdateInventoryWarehouseUseCase {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public OperationResult updateInventoryCount(Inventory inventory) {
        return inventoryRepository.updateInventoryCount(inventory);
    }

    @Override
    public OperationResult updateInventoryWarehouse(Inventory inventory) {
        return  inventoryRepository.updateInventoryWarehouse(inventory);
    }
}
