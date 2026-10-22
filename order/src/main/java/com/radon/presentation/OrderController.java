package com.radon.presentation;

import com.radon.OperationResult;
import com.radon.application.port.in.AddToCartUseCase;
import com.radon.application.port.in.RemoveFromCartUseCase;
import com.radon.domain.Product;
import com.radon.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    private final AddToCartUseCase addToCartUseCase;
    private final RemoveFromCartUseCase removeFromCartUseCase;

    public OrderController(AddToCartUseCase addToCartUseCase, RemoveFromCartUseCase removeFromCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
        this.removeFromCartUseCase = removeFromCartUseCase;
    }

    @PostMapping("{productId}")
    public ResponseEntity<Response<OperationResult>> addToCart(@PathVariable Long productId){
        return ResponseEntity.ok(
          new Response<>(
                  addToCartUseCase.addToCart(Product.builder().id(productId).build())
          )
        );
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Response<OperationResult>> removeFromCart(@PathVariable Long productId){
        return ResponseEntity.ok(
                new Response<>(
                        removeFromCartUseCase.removeFromCart(Product.builder().id(productId).build())
                )
        );
    }

    @GetMapping
    public String order(){
        return "order";
    }
}
