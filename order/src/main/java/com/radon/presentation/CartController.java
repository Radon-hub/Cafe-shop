package com.radon.presentation;

import com.radon.OperationResult;
import com.radon.application.port.in.AddToCartUseCase;
import com.radon.application.port.in.GetCartUseCase;
import com.radon.application.port.in.RemoveFromCartUseCase;
import com.radon.application.port.in.UpdateCartUseCase;
import com.radon.domain.Product;
import com.radon.presentation.dto.CartResponse;
import com.radon.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private final AddToCartUseCase addToCartUseCase;
    private final RemoveFromCartUseCase removeFromCartUseCase;
    private final GetCartUseCase getCartUseCase;
    private final UpdateCartUseCase updateCartUseCase;

    public CartController(AddToCartUseCase addToCartUseCase, RemoveFromCartUseCase removeFromCartUseCase, GetCartUseCase getCartUseCase, UpdateCartUseCase updateCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
        this.removeFromCartUseCase = removeFromCartUseCase;
        this.getCartUseCase = getCartUseCase;
        this.updateCartUseCase = updateCartUseCase;
    }

    @GetMapping
    public ResponseEntity<Response<CartResponse>> getCart() {
        return ResponseEntity.ok(
                new Response<>(
                        CartResponse.of(
                                getCartUseCase.getCart()
                        )
                )
        );
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

    @PutMapping("update/{cartId}")
    public ResponseEntity<Response<OperationResult>> updateCart(@PathVariable UUID cartId){
        return ResponseEntity.ok(
                new Response<>(
                        updateCartUseCase.updateCart(cartId)
                )
        );
    }
}
