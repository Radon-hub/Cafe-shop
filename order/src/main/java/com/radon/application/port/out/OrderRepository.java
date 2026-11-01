package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.domain.Order;

public interface OrderRepository {
    OperationResult submitOrder(Order order);
}
