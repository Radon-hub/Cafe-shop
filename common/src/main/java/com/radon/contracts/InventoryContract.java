package com.radon.contracts;

import lombok.Builder;

@Builder
public record InventoryContract(
        Long id,
        Long productId,
        Integer count,
        String wareHouse
) {
}
