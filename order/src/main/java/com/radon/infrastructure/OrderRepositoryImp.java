package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetProductContractsByIdsUseCase;
import com.radon.application.port.out.OrderRepository;
import com.radon.contracts.GetInventoryContractsByProductIdsUseCase;
import com.radon.contracts.InventoryContract;
import com.radon.contracts.ProductContract;
import com.radon.contracts.UserContract;
import com.radon.domain.CartStatus;
import com.radon.domain.OrderNumberGenerator;
import com.radon.domain.OrderStatus;
import com.radon.exception.types.*;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.entity.OrderEntity;
import com.radon.infrastructure.entity.OrderItemEntity;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import com.radon.infrastructure.repository.OrderItemJpaRepository;
import com.radon.infrastructure.repository.OrderJpaRepository;
import com.radon.presentation.dto.SubmitOrderRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetProductContractsByIdsUseCase getProductContractsByIdsUseCase;
    private final GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase;

    public OrderRepositoryImp(OrderJpaRepository orderJpaRepository, OrderItemJpaRepository orderItemJpaRepository, CartJpaRepository cartJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, GetProductContractsByIdsUseCase getProductContractsByIdsUseCase, GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderItemJpaRepository = orderItemJpaRepository;
        this.cartJpaRepository = cartJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductContractsByIdsUseCase = getProductContractsByIdsUseCase;
        this.getInventoryContractsByProductIdsUseCase = getInventoryContractsByProductIdsUseCase;
    }

    @Override
    @Transactional
    public OperationResult submitOrder(SubmitOrderRequest request) {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findById(request.cartId()).orElseThrow(CartNotFoundException::new);

        switch (cartEntity.getStatus()) {
            case ACTIVE -> {}
            case NEED_UPDATE -> throw new CartNeedUpdateException();
            default -> throw new CartNotAvailableException();
        }

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        List<ProductContract> productContractsByIds = getProductContractsByIdsUseCase.getProductContractsByIds(listOfProductIds);

        Map<Long, Integer> quantitiesByProductId =
                cartEntity.getItems().stream()
                        .collect(Collectors.toMap(
                                CartItemEntity::getProductId,
                                CartItemEntity::getQuantity
                        ));

        List<InventoryContract> inventories = getInventoryContractsByProductIdsUseCase.getInventoryContractByProductId(listOfProductIds);

        inventories.forEach(inventory -> {
            if(inventory.count() == 0){
                throw new ProductOutOfStockException(inventory.productId().toString());
            }
            if(quantitiesByProductId.get(inventory.productId()) > inventory.count()){
                throw new ProductNotEnoughAvailableQuantityException(inventory.productId().toString());
            }
        });

        BigDecimal totalPrice = productContractsByIds.stream()
                .map(product -> product.price().multiply(
                        BigDecimal.valueOf(
                                quantitiesByProductId.getOrDefault(product.id(), 0)
                        )
                ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity savedOrder = orderJpaRepository.save(new OrderEntity(
                OrderNumberGenerator.generateOrderNumber(),
                user.id(),
                OrderStatus.CREATED,
                totalPrice,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                request.address()
        ));

        List<OrderItemEntity> listOfOrderItems = productContractsByIds.stream().map(item ->
                new OrderItemEntity(
                        item.id(),
                        quantitiesByProductId.getOrDefault(item.id(), 0),
                        item.price(),
                        savedOrder,
                        item.name(),
                        item.description(),
                        item.price().multiply(
                                BigDecimal.valueOf(
                                        quantitiesByProductId.getOrDefault(item.id(), 0)
                                )
                        )
                )
        ).toList();

        orderItemJpaRepository.saveAll(listOfOrderItems);

        cartEntity.setStatus(CartStatus.CHECKED_OUT);

        return OperationResult.SUCCESS;
    }


}
