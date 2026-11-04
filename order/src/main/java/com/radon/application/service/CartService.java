package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.AddToCartUseCase;
import com.radon.application.port.in.GetCartUseCase;
import com.radon.application.port.in.RemoveFromCartUseCase;
import com.radon.application.port.in.UpdateCartStatusUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.domain.Cart;
import com.radon.domain.CartStatus;
import com.radon.domain.Product;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService implements AddToCartUseCase, RemoveFromCartUseCase, GetCartUseCase, UpdateCartStatusUseCase {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public OperationResult addToCart(Product product) {
        return cartRepository.addToCart(product);
    }

    @Override
    public OperationResult removeFromCart(Product product) {
        return cartRepository.removeFromCart(product);
    }

    @Override
    public Cart getCart() {
        return cartRepository.getCart();
    }

    @Override
    public void updateCartStatus(UUID cartId, CartStatus cartStatus) {
        cartRepository.updateCartStatus(cartId,cartStatus);
    }
}
