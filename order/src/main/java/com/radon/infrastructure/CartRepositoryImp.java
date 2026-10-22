package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.domain.Cart;
import com.radon.domain.Product;
import com.radon.domain.User;
import com.radon.exception.types.CartNotFoundException;
import com.radon.exception.types.ProductNotFoundException;
import com.radon.exception.types.ProductNotFoundInCartException;
import com.radon.exception.types.UserNotFoundException;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import com.radon.infrastructure.jpa.UserJpaRepository;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartRepositoryImp implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final UserJpaRepository userJpaRepository;

    public CartRepositoryImp(CartJpaRepository cartJpaRepository, CartItemJpaRepository cartItemJpaRepository, ProductJpaRepository productJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, UserJpaRepository userJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartItemJpaRepository = cartItemJpaRepository;
        this.productJpaRepository = productJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    @Transactional
    public OperationResult addToCart(Product product) {

        ProductEntity productEntity = productJpaRepository.findById(product.id()).orElseThrow(() -> new ProductNotFoundException(product.id().toString()));

        User user = getCurrentUserUseCase.getCurrentUser();

        UserEntity userEntity = userJpaRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFoundException(user.getUsername()));

        Optional<CartEntity> cartExists = cartJpaRepository.findByUser_Id(user.id());

        if(cartExists.isEmpty()){
            CartEntity cartEntity = cartJpaRepository.save(new CartEntity(userEntity));
            cartItemJpaRepository.save(new CartItemEntity(cartEntity,productEntity,1));
        }else{

            Optional<CartItemEntity> existingItem = cartItemJpaRepository.findByProduct_IdAndCart_Id(productEntity.getId(),cartExists.get().getId());

            if(existingItem.isPresent()){
                CartItemEntity cartItemEntity = existingItem.get();
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
            }else{
                cartItemJpaRepository.save(new CartItemEntity(cartExists.get(),productEntity,1));
            }

        }

        return OperationResult.SUCCESS;

    }

    @Override
    @Transactional
    public OperationResult removeFromCart(Product product) {

        ProductEntity productEntity = productJpaRepository.findById(product.id()).orElseThrow(() -> new ProductNotFoundException(product.id().toString()));

        User user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartExists = cartJpaRepository.findByUser_Id(user.id()).orElseThrow(CartNotFoundException::new);

        CartItemEntity existingItem = cartItemJpaRepository.findByProduct_IdAndCart_Id(productEntity.getId(),cartExists.getId()).orElseThrow(() -> new ProductNotFoundInCartException(productEntity.getId()));

        if(existingItem.getQuantity() > 1){
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        }else{
            cartItemJpaRepository.delete(existingItem);
        }

        return OperationResult.SUCCESS;

    }


}
