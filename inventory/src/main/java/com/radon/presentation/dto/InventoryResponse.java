package com.radon.presentation.dto;

import com.radon.domain.Inventory;

public record InventoryResponse(
        Long productId,
        Integer count,
        WarehouseResponse wareHouse
) {
    public static InventoryResponse of(Inventory inventory) {
        return new InventoryResponse(inventory.productId(),inventory.count(),WarehouseResponse.of(inventory.wareHouse()));
    }
}
