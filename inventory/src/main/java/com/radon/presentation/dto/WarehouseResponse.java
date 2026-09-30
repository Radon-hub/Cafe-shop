package com.radon.presentation.dto;

import com.radon.domain.Warehouse;

public record WarehouseResponse(
        Long id,
        String warehouse
) {
    public static WarehouseResponse of(Warehouse warehouse) {
        return new WarehouseResponse(warehouse.id(),warehouse.warehouse());
    }
}
