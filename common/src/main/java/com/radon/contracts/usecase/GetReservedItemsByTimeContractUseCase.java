package com.radon.contracts.usecase;

import com.radon.contracts.dto.ReservationStatus;
import com.radon.contracts.dto.ReservedContract;
import java.time.Instant;
import java.util.List;

public interface GetReservedItemsByTimeContractUseCase {
    List<ReservedContract> getReservedItemsByTimeUseCase(ReservationStatus status, Instant expiresAtIsLessThan);
}
