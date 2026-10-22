package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetUserByUsernameUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.domain.Cart;
import com.radon.domain.Product;
import com.radon.domain.User;
import com.radon.exception.types.ProductNotFoundException;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CartRepositoryImp implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final GetUserByUsernameUseCase getUserByUsernameUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private User currentUser;

    public CartRepositoryImp(CartJpaRepository cartJpaRepository, CartItemJpaRepository cartItemJpaRepository, ProductJpaRepository productJpaRepository, GetUserByUsernameUseCase getUserByUsernameUseCase, GetCurrentUserUseCase getCurrentUserUseCase) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartItemJpaRepository = cartItemJpaRepository;
        this.productJpaRepository = productJpaRepository;
        this.getUserByUsernameUseCase = getUserByUsernameUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.currentUser = getCurrentUserUseCase.getCurrentUser();
    }

    @Override
    public OperationResult addToCart(Product product) {

        ProductEntity productEntity = productJpaRepository.findById(product.id()).orElseThrow(() -> new ProductNotFoundException(product.id().toString()));

        User user = getUserByUsernameUseCase.getUserByUsername(currentUser.userName());

        Optional<CartEntity> cartExists = cartJpaRepository.findByUser_Id(user.id());

        if(cartExists.isEmpty()){
            cartJpaRepository.
        }

    }

    @Override
    public Cart createCart(UserEntity user) {
        CartEntity cartEntity = cartJpaRepository.save(new CartEntity(user));
        return Cart.
    }
}
