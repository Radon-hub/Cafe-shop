package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetProductContractsByIdsUseCase;
import com.radon.application.port.out.OrderRepository;
import com.radon.contracts.ProductContract;
import com.radon.contracts.UserContract;
import com.radon.domain.CartStatus;
import com.radon.domain.Order;
import com.radon.domain.OrderNumberGenerator;
import com.radon.domain.OrderStatus;
import com.radon.exception.types.UserNotFoundException;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.entity.OrderEntity;
import com.radon.infrastructure.entity.OrderItemEntity;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import com.radon.infrastructure.repository.OrderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetProductContractsByIdsUseCase getProductContractsByIdsUseCase;
    private final CartItemJpaRepository cartItemJpaRepository;

    public OrderRepositoryImp(OrderJpaRepository orderJpaRepository, CartJpaRepository cartJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, GetProductContractsByIdsUseCase getProductContractsByIdsUseCase, CartItemJpaRepository cartItemJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.cartJpaRepository = cartJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductContractsByIdsUseCase = getProductContractsByIdsUseCase;
        this.cartItemJpaRepository = cartItemJpaRepository;
    }

    @Override
    @Transactional
    public OperationResult submitOrder(Order order) {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatus(user.id(), CartStatus.ACTIVE).orElseThrow(() -> new UserNotFoundException(user.userName()));

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        List<ProductContract> productContractsByIds = getProductContractsByIdsUseCase.getProductContractsByIds(listOfProductIds);

        Map<Long, Integer> quantitiesByProductId =
                cartEntity.getItems().stream()
                        .collect(Collectors.toMap(
                                CartItemEntity::getProductId,
                                CartItemEntity::getQuantity
                        ));

        BigDecimal totalPrice = productContractsByIds.stream()
                .map(product -> product.price().multiply(
                        BigDecimal.valueOf(
                                quantitiesByProductId.getOrDefault(product.id(), 0)
                        )
                ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderJpaRepository.save(new OrderEntity(
                OrderNumberGenerator.generateOrderNumber(),
                user.id(),
                OrderStatus.CREATED,
                totalPrice,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                order.address(),
                new OrderItemEntity()
        ));

        return null;
    }


}
