package com.radon.presentation;

import com.radon.application.port.in.AddWarehouseUseCase;
import com.radon.application.port.in.UpdateWarehouseUseCase;
import com.radon.domain.Warehouse;
import com.radon.presentation.dto.WarehouseRequest;
import com.radon.presentation.dto.WarehouseResponse;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse")
public class WarehouseController {

    private final AddWarehouseUseCase addWarehouseUseCase;
    private final UpdateWarehouseUseCase updateWarehouseUseCase;

    public WarehouseController(AddWarehouseUseCase addWarehouseUseCase, UpdateWarehouseUseCase updateWarehouseUseCase) {
        this.addWarehouseUseCase = addWarehouseUseCase;
        this.updateWarehouseUseCase = updateWarehouseUseCase;
    }

    @PostMapping
    public ResponseEntity<Response<WarehouseResponse>> addNewWarehouse(@RequestBody WarehouseRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        WarehouseResponse.of(addWarehouseUseCase.addNewWarehouse(request.name()))
                )
        );
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<WarehouseResponse>> updateWarehouse(@PathVariable("id") Long id,@RequestBody WarehouseRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response<>(
                        WarehouseResponse.of(updateWarehouseUseCase.updateWarehouse(Warehouse.builder()
                                .id(id)
                                .warehouse(request.name())
                                .build()
                            )
                        )
                )
        );
    }
}
