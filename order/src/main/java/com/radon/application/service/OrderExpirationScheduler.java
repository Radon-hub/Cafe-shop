package com.radon.application.service;

import com.radon.contracts.dto.InventoryContract;
import com.radon.contracts.dto.ReservationOperator;
import com.radon.contracts.dto.ReservationStatus;
import com.radon.contracts.dto.ReservedContract;
import com.radon.contracts.usecase.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderExpirationScheduler {

    private final GetReservedItemsByTimeContractUseCase getReservedItemsByTimeContractUseCase;
    private final ReleaseReserveItemsByIdsContractUseCase releaseReserveItemsByIdsContractUseCase;
    private final ExpireOrdersByIdsContractUseCase expireOrdersByIdsContractUseCase;
    private final UpdateInventoryReserveContractUseCase updateInventoryReserveContractUseCase;
    private final GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase;

    public OrderExpirationScheduler(GetReservedItemsByTimeContractUseCase getReservedItemsByTimeContractUseCase, ReleaseReserveItemsByIdsContractUseCase releaseReserveItemsByIdsContractUseCase, ExpireOrdersByIdsContractUseCase expireOrdersByIdsContractUseCase, UpdateInventoryReserveContractUseCase updateInventoryReserveContractUseCase, GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase) {
        this.getReservedItemsByTimeContractUseCase = getReservedItemsByTimeContractUseCase;
        this.releaseReserveItemsByIdsContractUseCase = releaseReserveItemsByIdsContractUseCase;
        this.expireOrdersByIdsContractUseCase = expireOrdersByIdsContractUseCase;
        this.updateInventoryReserveContractUseCase = updateInventoryReserveContractUseCase;
        this.getInventoryContractsByProductIdsUseCase = getInventoryContractsByProductIdsUseCase;
    }

    @Scheduled(fixedDelay = 30,timeUnit = TimeUnit.SECONDS)
    public void expirePendingOrders() {

        log.info("ExpirePendingOrders -> Start");

        List<ReservedContract> reservedItems = getReservedItemsByTimeContractUseCase.getReservedItemsByTimeUseCase(ReservationStatus.ACTIVE, Instant.now());

        if(reservedItems.isEmpty()){
            log.info("ExpirePendingOrders -> No reserve item found.");
            return;
        }

        log.info("ExpirePendingOrders -> Pending orders that are expired -> count: {}",reservedItems.size());

        Set<UUID> orderIds = reservedItems.stream().map(ReservedContract::orderId).collect(Collectors.toSet());
        Set<Long> productIds = reservedItems.stream().map(ReservedContract::productId).collect(Collectors.toSet());

        expireOrdersByIdsContractUseCase.expireByOrderIds(orderIds.stream().toList());

        log.info("ExpirePendingOrders -> Expire orders done successfully");

        releaseReserveItemsByIdsContractUseCase.releaseReserveItemsByOrderIds(orderIds.stream().toList());
        log.info("ExpirePendingOrders -> Release orders done successfully");


        List<InventoryContract> inventories = getInventoryContractsByProductIdsUseCase.getInventoryContractByProductIds(productIds.stream().toList());
        log.info("ExpirePendingOrders -> Find inventories by reserved product ids -> inventories size:{} ",inventories.size());

        Map<Long,Integer> inventoriesAndCounts = new HashMap<>();

        for(InventoryContract inventoryContract : inventories){
            reservedItems.stream().filter(item -> item.productId().equals(inventoryContract.productId())).findFirst().ifPresent(item -> {
                inventoriesAndCounts.put(inventoryContract.id(),item.count());
            });
        }

        updateInventoryReserveContractUseCase.updateInventoryReserveContractUseCase(inventoriesAndCounts, ReservationOperator.DECREASE);
        log.info("ExpirePendingOrders -> Update inventory reserved counts");

        log.info("ExpirePendingOrders -> FINISH");

    }

}
