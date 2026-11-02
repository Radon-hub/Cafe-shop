package com.radon.contracts;

import lombok.Builder;

@Builder
public record InventoryContract(
        Long productId,
        Integer count,
        String wareHouse
) {
}
