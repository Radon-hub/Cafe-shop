package com.radon.infrastructure.repository;

import com.radon.application.port.out.WarehouseRepository;
import com.radon.domain.Warehouse;
import com.radon.exception.types.WarehouseExistsException;
import com.radon.exception.types.WarehouseNotFoundException;
import com.radon.infrastructure.entity.WarehouseEntity;
import com.radon.infrastructure.jpa.WarehouseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class WarehouseRepositoryImp implements WarehouseRepository {

    private final WarehouseJpaRepository warehouseJpaRepository;

    public WarehouseRepositoryImp(WarehouseJpaRepository warehouseJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
    }

    @Override
    public Warehouse findWarehouseByName(String name) {

        WarehouseEntity warehouseEntity = warehouseJpaRepository.findByWarehouse(name).orElseThrow(() -> new WarehouseNotFoundException(name));

        return Warehouse.of(warehouseEntity);
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse) {

        WarehouseEntity existed = warehouseJpaRepository.findById(warehouse.id())
                .orElseThrow(() -> new WarehouseNotFoundException(warehouse.id()));

        existed.setWarehouse(warehouse.warehouse());

        warehouseJpaRepository.save(existed);

        return warehouse;
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {

        Optional<WarehouseEntity> existed = warehouseJpaRepository.findByWarehouse(warehouse.warehouse());

        if(existed.isPresent()){
            throw new WarehouseExistsException(warehouse.warehouse());
        }

        warehouseJpaRepository.save(WarehouseEntity.of(warehouse));

        return warehouse;
    }

    @Override
    public WarehouseEntity findWarehouseById(Long id) {
        return warehouseJpaRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException(id));
    }
}
