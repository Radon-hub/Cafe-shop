package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.UpdateCartStatusUseCase;
import com.radon.application.port.out.OrderRepository;
import com.radon.contracts.dto.*;
import com.radon.contracts.usecase.GetProductsInventoriesContractUseCase;
import com.radon.contracts.usecase.ReserveProductsForOrderContractUseCase;
import com.radon.contracts.usecase.UpdateInventoryReserveContractUseCase;
import com.radon.domain.CartStatus;
import com.radon.domain.OrderNumberGenerator;
import com.radon.domain.OrderStatus;
import com.radon.exception.types.*;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.entity.OrderEntity;
import com.radon.infrastructure.entity.OrderItemEntity;
import com.radon.infrastructure.repository.CartJpaRepository;
import com.radon.infrastructure.repository.OrderItemJpaRepository;
import com.radon.infrastructure.repository.OrderJpaRepository;
import com.radon.presentation.dto.SubmitOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final CartJpaRepository cartJpaRepository;

    private final UpdateCartStatusUseCase updateCartStatusUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetProductsInventoriesContractUseCase getProductsInventoriesContractUseCase;
    private final ReserveProductsForOrderContractUseCase reserveProductsForOrderContractUseCase;
    private final UpdateInventoryReserveContractUseCase updateInventoryReserveContractUseCase;

    public OrderRepositoryImp(OrderJpaRepository orderJpaRepository, OrderItemJpaRepository orderItemJpaRepository, CartJpaRepository cartJpaRepository, UpdateCartStatusUseCase updateCartStatusUseCase, GetCurrentUserUseCase getCurrentUserUseCase, GetProductsInventoriesContractUseCase getProductsInventoriesContractUseCase, ReserveProductsForOrderContractUseCase reserveProductsForOrderContractUseCase, UpdateInventoryReserveContractUseCase updateInventoryReserveContractUseCase) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderItemJpaRepository = orderItemJpaRepository;
        this.cartJpaRepository = cartJpaRepository;
        this.updateCartStatusUseCase = updateCartStatusUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductsInventoriesContractUseCase = getProductsInventoriesContractUseCase;
        this.reserveProductsForOrderContractUseCase = reserveProductsForOrderContractUseCase;
        this.updateInventoryReserveContractUseCase = updateInventoryReserveContractUseCase;
    }

    @Override
    @Transactional
    public OperationResult expirePendingOrders(List<UUID> orderIds) {
        orderJpaRepository.expireByOrderIds(orderIds);
        return OperationResult.SUCCESS;
    }

    @Override
    @Transactional
    public OperationResult submitOrder(SubmitOrderRequest request) {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findById(request.cartId()).orElseThrow(CartNotFoundException::new);

        switch (cartEntity.getStatus()) {
            case ACTIVE -> {
                log.info("SubmitOrder -> user:{},cart:{}", user, cartEntity);
            }
            case NEED_UPDATE -> {
                log.error("SubmitOrder -> CartNeedUpdateException -> user:{},cart:{}", user, cartEntity);
                throw new CartNeedUpdateException();
            }
            default -> {
                log.error("SubmitOrder -> CartNotAvailableException -> user:{},cart:{}", user, cartEntity);
                throw new CartNotAvailableException();
            }
        }

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());


        Map<Long, Integer> quantitiesByProductId =
                cartEntity.getItems().stream()
                        .collect(Collectors.toMap(
                                CartItemEntity::getProductId,
                                CartItemEntity::getQuantity
                        ));


        List<ProductInventoryContract> productsInventories = getProductsInventoriesContractUseCase.getProductsInventories(listOfProductIds);

        List<InventoryContract> inventories = productsInventories.stream().map(ProductInventoryContract::inventory).toList();

        inventories.forEach(inventory -> {
            if (inventory.count() == 0) {
                updateCartStatusUseCase.updateCartStatus(cartEntity.getId(), CartStatus.NEED_UPDATE);
                log.error("SubmitOrder -> ProductOutOfStockException -> productId:{} ", inventory.productId());
                throw new ProductOutOfStockException(inventory.productId().toString());
            }
            if (quantitiesByProductId.get(inventory.productId()) > inventory.count()) {
                updateCartStatusUseCase.updateCartStatus(cartEntity.getId(), CartStatus.NEED_UPDATE);
                log.error("AddToCart -> ProductNotEnoughAvailableQuantityException -> productId:{} , inventoryCount:{} , cartQuantity:{}", inventory.productId(), inventory.count(), quantitiesByProductId.getOrDefault(inventory.productId(), 0));
                throw new ProductNotEnoughAvailableQuantityException(inventory.productId().toString());
            }
        });

        BigDecimal totalPrice = productsInventories.stream()
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

        log.info("SubmitOrder -> Order submitted -> order:{} ", savedOrder);

        List<OrderItemEntity> listOfOrderItems = productsInventories.stream().map(item ->
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

        log.info("SubmitOrder -> Order items saved -> order:{} ", listOfOrderItems);

        log.info("SubmitOrder -> Redirect for Reservation ===> ReserveProducts");

        Map<UUID, Map<Long, Integer>> ordersWithProduct = new HashMap<>();

        Map<Long, Integer> collectOrderProducts = listOfOrderItems.stream().collect(Collectors.toMap(
                OrderItemEntity::getProductId,
                OrderItemEntity::getQuantity
        ));

        ordersWithProduct.put(savedOrder.getId(), collectOrderProducts);

        List<ReservedContract> reservedContracts = reserveProductsForOrderContractUseCase.reserveProductForOrder(ordersWithProduct);

        log.info("SubmitOrder <=== Receive the reservations -> reserved products:{} ", reservedContracts);

        Map<Long, Integer> inventoriesWithReservedCounts = new HashMap<>();

        for (ReservedContract reservedContract : reservedContracts) {
            inventories.stream().filter(item -> item.productId().equals(reservedContract.productId())).findFirst()
                    .ifPresent(inventoryContract -> inventoriesWithReservedCounts.put(inventoryContract.id(), reservedContract.count()));
        }

        log.info("SubmitOrder -> Get inventories that need update on reserved counts -> inventories:{} ", inventoriesWithReservedCounts);

        updateInventoryReserveContractUseCase.updateInventoryReserveContractUseCase(inventoriesWithReservedCounts, ReservationOperator.INCREASE);

        cartEntity.setStatus(CartStatus.CHECKED_OUT);

        savedOrder.setOrderStatus(OrderStatus.PENDING_PAYMENT);

        log.info("SubmitOrder -> Cart status changed to CHECKED_OUT / Order status changed to PENDING_PAYMENT / ORDER - CART = LOCKED -> cart:{} ", cartEntity);

        log.info("SubmitOrder -> SUCCESS");

        return OperationResult.SUCCESS;
    }


}
