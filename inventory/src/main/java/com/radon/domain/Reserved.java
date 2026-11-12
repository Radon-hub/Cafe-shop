package com.radon.domain;

import com.radon.contracts.dto.ReservationStatus;
import com.radon.contracts.dto.ReservedContract;
import com.radon.infrastructure.entity.ReservedEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record Reserved(
        Long id,
        Long productId,
        UUID orderId,
        Integer count,
        ReservationStatus status
) {

    public static ReservedContract toContract(Reserved reserved) {
        return ReservedContract.builder()
                .id(reserved.id)
                .productId(reserved.productId)
                .orderId(reserved.orderId)
                .count(reserved.count)
                .status(reserved.status)
                .build();
    }

    public static Reserved fromEntity(ReservedEntity reservedEntity) {
        return Reserved.builder()
                .id(reservedEntity.getId())
                .productId(reservedEntity.getProductId())
                .orderId(reservedEntity.getOrderId())
                .count(reservedEntity.getCount())
                .status(reservedEntity.getStatus())
                .build();
    }


}
