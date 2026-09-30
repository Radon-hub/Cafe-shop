package com.radon.infrastructure.repository;

import com.radon.OperationResult;
import com.radon.application.port.out.InventoryRepository;
import com.radon.application.port.out.WarehouseRepository;
import com.radon.domain.Inventory;
import com.radon.exception.types.InventoryExistsException;
import com.radon.exception.types.InventoryNotFoundException;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.entity.WarehouseEntity;
import com.radon.infrastructure.jpa.InventoryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImp implements InventoryRepository {

    private final InventoryJpaRepository inventoryJpaRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryRepositoryImp(InventoryJpaRepository inventoryJpaRepository, WarehouseRepository warehouseRepository) {
        this.inventoryJpaRepository = inventoryJpaRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public InventoryEntity addInventory(ProductEntity productEntity,Inventory inventory) {

        if(inventoryJpaRepository.findByProduct_Id(inventory.productId()).isPresent()){
            throw new InventoryExistsException(inventory.productId());
        }

        WarehouseEntity warehouse = warehouseRepository.findWarehouseById(inventory.wareHouse().id());

        return inventoryJpaRepository.save(new InventoryEntity(
                productEntity,
                0,
                warehouse
        ));

    }

    @Override
    public OperationResult deleteInventory(ProductEntity product,Inventory inventory) {

        if(inventoryJpaRepository.findByProduct_Id(inventory.productId()).isEmpty()){
            throw new InventoryNotFoundException(inventory.productId());
        }

        inventoryJpaRepository.delete(InventoryEntity.of(product,inventory,inventory.wareHouse()));

        return OperationResult.SUCCESS;
    }
}
