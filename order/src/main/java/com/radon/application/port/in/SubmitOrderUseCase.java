package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.domain.Order;

public interface SubmitOrderUseCase {
    OperationResult submitOrder(Order order);
}
