package com.radon.application.port.out;

import com.radon.OperationResult;
import com.radon.domain.Cart;
import com.radon.domain.Product;
import com.radon.infrastructure.entity.UserEntity;

public interface CartRepository {
    OperationResult addToCart(Product product);

    Cart getCart();

    OperationResult removeFromCart(Product product);

    OperationResult createCart(UserEntity userEntity);
}
