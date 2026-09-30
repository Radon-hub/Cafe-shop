package com.radon.application.port.in;

import com.radon.domain.Warehouse;

public interface AddWarehouseUseCase {
    Warehouse addNewWarehouse(String name);
}
