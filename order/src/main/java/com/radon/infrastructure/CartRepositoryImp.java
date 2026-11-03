package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetProductContractByIdUseCase;
import com.radon.application.port.in.GetProductContractsByIdsUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.contracts.*;
import com.radon.domain.Cart;
import com.radon.domain.CartItem;
import com.radon.domain.CartStatus;
import com.radon.domain.Product;
import com.radon.exception.types.*;
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
    private final GetInventoryContractByProductIdUseCase getInventoryContractByProductIdUseCase;
    private final GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase;

    public CartRepositoryImp(CartJpaRepository cartJpaRepository, CartItemJpaRepository cartItemJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, GetProductContractByIdUseCase getProductContractByIdUseCase, GetProductContractsByIdsUseCase getProductContractsByIdsUseCase, GetInventoryContractByProductIdUseCase getInventoryContractByProductIdUseCase, GetInventoryContractsByProductIdsUseCase getInventoryContractsByProductIdsUseCase) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartItemJpaRepository = cartItemJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductContractByIdUseCase = getProductContractByIdUseCase;
        this.getProductContractsByIdsUseCase = getProductContractsByIdsUseCase;
        this.getInventoryContractByProductIdUseCase = getInventoryContractByProductIdUseCase;
        this.getInventoryContractsByProductIdsUseCase = getInventoryContractsByProductIdsUseCase;
    }

    @Override
    @Transactional
    public OperationResult addToCart(Product product) {

        ProductContract productContract = getProductContractByIdUseCase.getProductContractById(product.id());

        InventoryContract inventory = getInventoryContractByProductIdUseCase.getInventoryContractByProductId(product.id());

        if(inventory.count() == 0){
            throw new ProductOutOfStockException(inventory.productId().toString());
        }

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(), List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE))
                .orElseGet(() -> createCart(user.id()));

        Optional<CartItemEntity> existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productContract.id(), cartEntity.getId());

        if (existingItem.isPresent()) {

            CartItemEntity cartItemEntity = existingItem.get();

            if(cartItemEntity.getQuantity() >= inventory.count()){
                throw new ProductNotEnoughAvailableQuantityException(inventory.productId().toString());
            }

            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);

        } else {
            cartItemJpaRepository.save(new CartItemEntity(cartEntity, productContract.id(), 1));
        }

        return OperationResult.SUCCESS;

    }

    @Override
    @Transactional
    public Cart getCart() {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(),List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE)).orElseThrow(() -> new UserNotFoundException(user.userName()));

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        List<ProductContract> productContractsByIds = getProductContractsByIdsUseCase.getProductContractsByIds(listOfProductIds);

        boolean doCartNeedUpdate = cartEntity.getStatus().equals(CartStatus.NEED_UPDATE);

        if(!doCartNeedUpdate){
            List<InventoryContract> inventories = getInventoryContractsByProductIdsUseCase.getInventoryContractByProductId(listOfProductIds);

            Map<Long, Integer> inventoryMap = inventories.stream().collect(
                    Collectors.toMap(
                            InventoryContract::productId,
                            InventoryContract::count
                    )
            );

            doCartNeedUpdate = cartEntity.getItems().stream().anyMatch(item -> {
                Integer availableCount = inventoryMap.get(item.getProductId());
                return availableCount != null && item.getQuantity() > availableCount;
            });

            if(doCartNeedUpdate){
                cartEntity.setStatus(CartStatus.NEED_UPDATE);
            }
        }

        return Cart.builder()
                .id(cartEntity.getId())
                .status(cartEntity.getStatus())
                .needUpdate(doCartNeedUpdate)
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

        CartEntity cartExists = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(),List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE)).orElseThrow(CartNotFoundException::new);

        CartItemEntity existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productContract.id(), cartExists.getId()).orElseThrow(() -> new ProductNotFoundInCartException(productContract.id()));

        if (existingItem.getQuantity() > 1) {
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        } else {
            cartItemJpaRepository.delete(existingItem);
        }

        return OperationResult.SUCCESS;

    }

    @Override
    @Transactional
    public CartEntity createCart(UUID customerId) {
        return cartJpaRepository.save(new CartEntity(customerId));
    }


}
