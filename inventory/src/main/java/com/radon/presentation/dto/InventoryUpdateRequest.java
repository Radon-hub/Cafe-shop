package com.radon.presentation.dto;

import com.radon.exception.types.IllegalStateException;
import lombok.Builder;

@Builder
public record InventoryUpdateRequest(
        Long productId,
        Integer count,
        Long wareHouseId
) {

    public InventoryUpdateRequest {
        if(productId == null) throw new IllegalStateException("Product ID can not be null!");
        if(count < 0) throw new IllegalStateException("Count cannot be negative!");
    }

    public InventoryUpdateRequest(Long productId, Integer count) {
        this(productId,count,null);
    }

}
