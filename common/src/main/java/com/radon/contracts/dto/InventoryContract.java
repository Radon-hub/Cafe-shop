package com.radon.contracts.dto;

import lombok.Builder;

@Builder
public record InventoryContract(
        Long id,
        Long productId,
        Integer count,
        String wareHouse
) {
}
