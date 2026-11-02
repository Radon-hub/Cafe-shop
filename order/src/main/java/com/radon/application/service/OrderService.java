package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.SubmitOrderUseCase;
import com.radon.application.port.out.OrderRepository;
import com.radon.domain.Order;
import com.radon.presentation.dto.SubmitOrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements SubmitOrderUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OperationResult submitOrder(SubmitOrderRequest request) {
        return orderRepository.submitOrder(request);
    }
}
