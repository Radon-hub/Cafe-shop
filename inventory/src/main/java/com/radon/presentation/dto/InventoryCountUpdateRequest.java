package com.radon.presentation.dto;

import com.radon.exception.types.IllegalStateException;
import lombok.Builder;

public record InventoryCountUpdateRequest(
        Integer count
) {
    public InventoryCountUpdateRequest {
        if(count < 0) throw new IllegalStateException("Count cannot be negative!");
    }
}
