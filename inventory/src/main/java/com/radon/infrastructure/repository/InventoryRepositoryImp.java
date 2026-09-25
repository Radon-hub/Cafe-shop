package com.radon.infrastructure.repository;

import com.radon.OperationResult;
import com.radon.application.port.out.InventoryRepository;
import com.radon.domain.Inventory;
import com.radon.exception.types.InventoryExistsException;
import com.radon.exception.types.InventoryNotFoundException;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.jpa.InventoryJpaRepository;

public class InventoryRepositoryImp implements InventoryRepository {

    private final InventoryJpaRepository inventoryJpaRepository;

    public InventoryRepositoryImp(InventoryJpaRepository inventoryJpaRepository) {
        this.inventoryJpaRepository = inventoryJpaRepository;
    }

    @Override
    public Inventory addInventory(Inventory inventory) {

        if(inventoryJpaRepository.findByProduct_Id(inventory.productId()).isPresent()){
            throw new InventoryExistsException(inventory.productId());
        }

        return Inventory.of(
                inventoryJpaRepository.save(InventoryEntity.of(inventory))
        );
    }

    @Override
    public OperationResult deleteInventory(Inventory inventory) {

        if(inventoryJpaRepository.findByProduct_Id(inventory.productId()).isEmpty()){
            throw new InventoryNotFoundException(inventory.productId());
        }

        inventoryJpaRepository.delete(InventoryEntity.of(inventory));

        return OperationResult.SUCCESS;
    }
}
