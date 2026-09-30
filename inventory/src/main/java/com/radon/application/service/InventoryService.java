package com.radon.application.service;

import com.radon.application.port.in.UpdateInventoryUseCase;
import com.radon.application.port.out.InventoryRepository;
import com.radon.domain.Inventory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService implements UpdateInventoryUseCase {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.updateInventory(inventory);
    }
}
