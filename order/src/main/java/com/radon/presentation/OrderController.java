package com.radon.presentation;

import com.radon.OperationResult;
import com.radon.application.port.in.SubmitOrderUseCase;
import com.radon.presentation.dto.SubmitOrderRequest;
import com.radon.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final SubmitOrderUseCase submitOrderUseCase;

    public OrderController(SubmitOrderUseCase submitOrderUseCase) {
        this.submitOrderUseCase = submitOrderUseCase;
    }

    @PostMapping("submit")
    public ResponseEntity<Response<OperationResult>> submitOrder(@RequestBody SubmitOrderRequest submitOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Response<>(
                        submitOrderUseCase.submitOrder(submitOrderRequest)
                )
        );
    }

}
