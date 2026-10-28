package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetProductContractByIdUseCase;
import com.radon.application.port.in.GetProductContractsByIdsUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.contracts.ProductContract;
import com.radon.contracts.UserContract;
import com.radon.domain.Cart;
import com.radon.domain.CartItem;
import com.radon.domain.Product;
import com.radon.exception.types.CartNotFoundException;
import com.radon.exception.types.ProductNotFoundInCartException;
import com.radon.exception.types.UserNotFoundException;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CartRepositoryImp implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetProductContractByIdUseCase getProductContractByIdUseCase;
    private final GetProductContractsByIdsUseCase getProductContractsByIdsUseCase;


    public CartRepositoryImp(CartJpaRepository cartJpaRepository, CartItemJpaRepository cartItemJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, GetProductContractByIdUseCase getProductContractByIdUseCase, GetProductContractsByIdsUseCase getProductContractsByIdsUseCase) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartItemJpaRepository = cartItemJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductContractByIdUseCase = getProductContractByIdUseCase;
        this.getProductContractsByIdsUseCase = getProductContractsByIdsUseCase;
    }

    @Override
    @Transactional
    public OperationResult addToCart(Product product) {

        ProductContract productContract = getProductContractByIdUseCase.getProductContractById(product.id());

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartExists = cartJpaRepository.findByCustomerId(user.id()).orElseThrow(CartNotFoundException::new);

        Optional<CartItemEntity> existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productContract.id(), cartExists.getId());

        if (existingItem.isPresent()) {
            CartItemEntity cartItemEntity = existingItem.get();
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
        } else {
            cartItemJpaRepository.save(new CartItemEntity(cartExists, productContract.id(), 1));
        }

        return OperationResult.SUCCESS;

    }

    @Override
    public Cart getCart() {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerId(user.id()).orElseThrow(() -> new UserNotFoundException(user.userName()));

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        List<ProductContract> productContractsByIds = getProductContractsByIdsUseCase.getProductContractsByIds(listOfProductIds);

        return Cart.builder()
                .id(cartEntity.getId())
                .items(cartEntity.getItems().stream().map( item -> {

                    ProductContract productContract = productContractsByIds.stream().filter(it -> it.id().equals(item.getProductId())).findFirst().orElse(null);

                    return CartItem.builder()
                         .id(item.getId())
                         .product(productContract)
                         .quantity(item.getQuantity())
                         .build();

                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public OperationResult removeFromCart(Product product) {

        ProductContract productContract = getProductContractByIdUseCase.getProductContractById(product.id());

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartExists = cartJpaRepository.findByCustomerId(user.id()).orElseThrow(CartNotFoundException::new);

        CartItemEntity existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productContract.id(), cartExists.getId()).orElseThrow(() -> new ProductNotFoundInCartException(productContract.id()));

        if (existingItem.getQuantity() > 1) {
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        } else {
            cartItemJpaRepository.delete(existingItem);
        }

        return OperationResult.SUCCESS;

    }

    @Override
    public OperationResult createCart(UUID customerId) {
        cartJpaRepository.save(new CartEntity(customerId));
        return OperationResult.SUCCESS;
    }


}
