package model;

import com.radon.domain.Inventory;
import com.radon.domain.Warehouse;
import com.radon.presentation.dto.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class InventoryResponseTest {

    Inventory inventory = Inventory.builder()
            .id(1L)
            .wareHouse(Warehouse.builder().id(1L).build())
            .count(5)
            .productId(1L)
            .build();

    @Test
    void of() {
        InventoryResponse inventoryResponse = InventoryResponse.of(inventory);
        Assertions.assertNotNull(inventoryResponse);
        log.info("[TEST] Success : {} | is Equal to -> {}", inventoryResponse.productId(),inventory.productId());
    }

}