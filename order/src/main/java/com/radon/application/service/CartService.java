package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.AddToCartUseCase;
import com.radon.application.port.in.CreateCartUseCase;
import com.radon.application.port.in.GetCartUseCase;
import com.radon.application.port.in.RemoveFromCartUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.domain.Cart;
import com.radon.domain.Product;
import com.radon.domain.User;
import com.radon.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService implements AddToCartUseCase, RemoveFromCartUseCase, GetCartUseCase, CreateCartUseCase {

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
    public OperationResult createCart(UserEntity userEntity) {
        return cartRepository.createCart(userEntity);
    }
}
