package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.contracts.GetInventoryContractByProductIdUseCase;
import com.radon.contracts.GetInventoryContractsByProductIdsUseCase;
import com.radon.application.port.in.UpdateInventoryCountUseCase;
import com.radon.application.port.in.UpdateInventoryWarehouseUseCase;
import com.radon.application.port.out.InventoryRepository;
import com.radon.contracts.InventoryContract;
import com.radon.domain.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService implements UpdateInventoryCountUseCase, UpdateInventoryWarehouseUseCase, GetInventoryContractsByProductIdsUseCase, GetInventoryContractByProductIdUseCase {

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

    @Override
    public InventoryContract getInventoryContractByProductId(Long productId) {
        return Inventory.toContract(inventoryRepository.getInventoryByProductId(productId));
    }

    @Override
    public List<InventoryContract> getInventoryContractByProductId(List<Long> productIds) {
        return inventoryRepository.getInventoriesByProductIds(productIds).stream().map(Inventory::toContract).collect(Collectors.toList());
    }
}
