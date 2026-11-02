package com.radon.application.port.in;

import com.radon.OperationResult;
import com.radon.presentation.dto.SubmitOrderRequest;

public interface SubmitOrderUseCase {
    OperationResult submitOrder(SubmitOrderRequest request);
}
