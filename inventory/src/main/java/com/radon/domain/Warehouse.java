package com.radon.domain;

import com.radon.infrastructure.entity.WarehouseEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record Warehouse(
        Long id,
        String warehouse,
        List<Inventory> inventory
) {
    public static Warehouse of(WarehouseEntity warehouseEntity) {
        return Warehouse.builder()
                .id(warehouseEntity.getId())
                .warehouse(warehouseEntity.getWarehouse())
                .inventory(warehouseEntity.getInventory().stream().map(Inventory::of).toList()).build();
    }
}
