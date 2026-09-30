package com.radon.application.service;

import com.radon.application.port.in.AddWarehouseUseCase;
import com.radon.application.port.in.UpdateWarehouseUseCase;
import com.radon.application.port.out.WarehouseRepository;
import com.radon.domain.Warehouse;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements AddWarehouseUseCase, UpdateWarehouseUseCase {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }


    @Override
    public Warehouse addNewWarehouse(String name) {
        return warehouseRepository.addWarehouse(Warehouse.builder().warehouse(name).build());
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.updateWarehouse(warehouse);
    }
}
