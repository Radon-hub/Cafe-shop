package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.contracts.dto.ReservationOperator;
import com.radon.contracts.usecase.GetInventoryContractByProductIdUseCase;
import com.radon.contracts.usecase.GetInventoryContractsByProductIdsUseCase;
import com.radon.application.port.in.UpdateInventoryCountUseCase;
import com.radon.application.port.in.UpdateInventoryWarehouseUseCase;
import com.radon.application.port.out.InventoryRepository;
import com.radon.contracts.dto.InventoryContract;
import com.radon.contracts.usecase.UpdateInventoryReserveContractUseCase;
import com.radon.domain.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService implements UpdateInventoryReserveContractUseCase,UpdateInventoryCountUseCase, UpdateInventoryWarehouseUseCase, GetInventoryContractsByProductIdsUseCase, GetInventoryContractByProductIdUseCase {

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
    public List<InventoryContract> getInventoryContractByProductIds(List<Long> productIds) {
        return inventoryRepository.getInventoriesByProductIds(productIds).stream().map(Inventory::toContract).collect(Collectors.toList());
    }

    @Override
    public OperationResult updateInventoryReserveContractUseCase(Map<Long,Integer> inventoriesAndReservedCounts, ReservationOperator operator) {
        return inventoryRepository.updateInventoryReserveCount(inventoriesAndReservedCounts,operator);
    }
}
