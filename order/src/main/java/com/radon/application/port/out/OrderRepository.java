package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.presentation.dto.SubmitOrderRequest;

public interface OrderRepository {
    OperationResult submitOrder(SubmitOrderRequest request);
}
