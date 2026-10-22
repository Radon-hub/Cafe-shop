package com.radon.application.service;

import com.radon.OperationResult;
import com.radon.application.port.in.AddToCartUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CartService implements AddToCartUseCase {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public OperationResult addToCart(Product product) {
        return cartRepository.addToCart(product);
    }

}
