package com.radon.application.port.out;

import com.radon.domain.Warehouse;
import com.radon.infrastructure.entity.WarehouseEntity;

public interface WarehouseRepository {
    Warehouse findWarehouseByName(String name);
    Warehouse updateWarehouse(Warehouse warehouse);
    Warehouse addWarehouse(Warehouse warehouse);
    WarehouseEntity findWarehouseById(Long id);
}
