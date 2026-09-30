package com.radon.presentation.dto;

import lombok.Builder;

@Builder
public record EditWarehouseRequest(
        Long id,
        String name
) {
}
