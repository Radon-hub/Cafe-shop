package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.presentation.dto.SubmitOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    OperationResult expirePendingOrders(List<UUID> orderIds);
    OperationResult submitOrder(SubmitOrderRequest request);
}
