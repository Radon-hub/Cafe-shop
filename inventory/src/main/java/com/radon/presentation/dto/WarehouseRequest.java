package com.radon.presentation.dto;

import lombok.Builder;

@Builder
public record WarehouseRequest(
        String name
) {

}
