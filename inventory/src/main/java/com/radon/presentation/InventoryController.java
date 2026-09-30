package com.radon.presentation;

import com.radon.application.port.in.UpdateInventoryUseCase;
import com.radon.domain.Inventory;
import com.radon.domain.Warehouse;
import com.radon.presentation.dto.InventoryResponse;
import com.radon.presentation.dto.InventoryUpdateRequest;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    private final UpdateInventoryUseCase updateInventoryUseCase;

    public InventoryController(UpdateInventoryUseCase updateInventoryUseCase) {
        this.updateInventoryUseCase = updateInventoryUseCase;
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<InventoryResponse>> updateInventory(@PathVariable Long id, @RequestBody InventoryUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                    InventoryResponse.of(
                            updateInventoryUseCase.updateInventory(Inventory.builder()
                                    .id(id)
                                    .count(request.count())
                                    .productId(request.productId())
                                    .wareHouse(Warehouse.builder().id(request.wareHouseId()).build())
                                    .build()
                            )
                    )
                )
        );
    }

}
