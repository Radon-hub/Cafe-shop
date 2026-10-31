package com.radon.presentation;

import com.radon.OperationResult;
import com.radon.application.port.in.UpdateInventoryCountUseCase;
import com.radon.application.port.in.UpdateInventoryWarehouseUseCase;
import com.radon.domain.Inventory;
import com.radon.domain.Warehouse;
import com.radon.presentation.dto.InventoryResponse;
import com.radon.presentation.dto.InventoryCountUpdateRequest;
import com.radon.presentation.dto.InventoryWarehouseUpdateRequest;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final UpdateInventoryCountUseCase updateInventoryCountUseCase;
    private final UpdateInventoryWarehouseUseCase updateInventoryWarehouseUseCase;

    public InventoryController(UpdateInventoryCountUseCase updateInventoryCountUseCase, UpdateInventoryWarehouseUseCase updateInventoryWarehouseUseCase) {
        this.updateInventoryCountUseCase = updateInventoryCountUseCase;
        this.updateInventoryWarehouseUseCase = updateInventoryWarehouseUseCase;
    }

    @PatchMapping("{inventoryId}/{productId}/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<OperationResult>> updateInventoryCount(@PathVariable Long inventoryId, @PathVariable Long productId, @RequestBody InventoryCountUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        updateInventoryCountUseCase.updateInventoryCount(Inventory.builder()
                                .id(inventoryId)
                                .count(request.count())
                                .productId(productId)
                                .build()
                        )
                )
        );
    }

    @PatchMapping("{inventoryId}/{productId}/warehouse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<OperationResult>> updateInventoryWarehouse(@PathVariable Long inventoryId, @PathVariable Long productId, @RequestBody InventoryWarehouseUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        updateInventoryWarehouseUseCase.updateInventoryWarehouse(Inventory.builder()
                                .id(inventoryId)
                                .productId(productId)
                                .wareHouse(Warehouse.builder().id(request.warehouseId()).build())
                                .build()
                        )
                )
        );
    }

}
