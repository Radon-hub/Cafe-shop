package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.SubmitOrderUseCase;
import com.radon.application.port.out.OrderRepository;
import com.radon.contracts.usecase.ExpireOrdersByIdsContractUseCase;
import com.radon.presentation.dto.SubmitOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements SubmitOrderUseCase, ExpireOrdersByIdsContractUseCase {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OperationResult submitOrder(SubmitOrderRequest request) {
        return orderRepository.submitOrder(request);
    }

    @Override
    public OperationResult expireByOrderIds(List<UUID> orderIds) {
        return orderRepository.expirePendingOrders(orderIds);
    }
}
