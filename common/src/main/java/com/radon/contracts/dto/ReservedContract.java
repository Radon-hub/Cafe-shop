package com.radon.contracts.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservedContract(
        Long id,
        Long productId,
        UUID orderId,
        Integer count,
        ReservationStatus status
) {

}
