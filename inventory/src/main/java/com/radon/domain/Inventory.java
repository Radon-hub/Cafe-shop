package com.radon.domain;

import com.radon.infrastructure.entity.InventoryEntity;
import lombok.Builder;

@Builder
public record Inventory(
        Long id,
        Long productId,
        Integer count,
        String wareHouse
) {

    public static Inventory of(InventoryEntity inventoryEntity) {
        return Inventory.builder()
                .id(inventoryEntity.getId())
                .count(inventoryEntity.getCount())
                .wareHouse(inventoryEntity.getWareHouse())
                .productId(inventoryEntity.getProduct().getId())
                .build();
    }


}
