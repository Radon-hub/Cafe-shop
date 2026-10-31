package com.radon.presentation.dto;


import com.radon.exception.types.IllegalStateException;

public record InventoryWarehouseUpdateRequest(
        Long warehouseId
) {
    public InventoryWarehouseUpdateRequest {
        if(warehouseId == null || warehouseId <= 0) throw new IllegalStateException("Warehouse id cannot be null or empty!");
    }
}
