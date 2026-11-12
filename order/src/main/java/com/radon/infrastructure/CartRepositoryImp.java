package com.radon.infrastructure;

import com.radon.OperationResult;
import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.GetProductContractByIdUseCase;
import com.radon.application.port.out.CartRepository;
import com.radon.contracts.dto.InventoryContract;
import com.radon.contracts.dto.ProductContract;
import com.radon.contracts.dto.ProductInventoryContract;
import com.radon.contracts.dto.UserContract;
import com.radon.contracts.usecase.GetProductsInventoriesContractUseCase;
import com.radon.domain.*;
import com.radon.exception.types.*;
import com.radon.infrastructure.entity.CartEntity;
import com.radon.infrastructure.entity.CartItemEntity;
import com.radon.infrastructure.repository.CartItemJpaRepository;
import com.radon.infrastructure.repository.CartJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CartRepositoryImp implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetProductsInventoriesContractUseCase getProductsInventoriesContractUseCase;
    private final GetProductContractByIdUseCase getProductContractByIdUseCase;

    public CartRepositoryImp(CartJpaRepository cartJpaRepository, CartItemJpaRepository cartItemJpaRepository, GetCurrentUserUseCase getCurrentUserUseCase, GetProductsInventoriesContractUseCase getProductsInventoriesContractUseCase, GetProductContractByIdUseCase getProductContractByIdUseCase) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartItemJpaRepository = cartItemJpaRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getProductsInventoriesContractUseCase = getProductsInventoriesContractUseCase;
        this.getProductContractByIdUseCase = getProductContractByIdUseCase;
    }

    @Override
    @Transactional
    public OperationResult addToCart(Product product) {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        ProductInventoryContract productInventoryContract = getProductsInventoriesContractUseCase.getProductsInventories(List.of(product.id())).getFirst();

        InventoryContract inventory = productInventoryContract.inventory();

        log.info("AddToCart -> productId:{} , userId:{} ",inventory.productId(),user.userName());

        if(inventory.count() == 0){
            log.error("AddToCart -> ProductOutOfStockException -> productId:{} ",inventory.productId());
            throw new ProductOutOfStockException(inventory.productId().toString());
        }

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(), List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE))
                .orElseGet(() -> createCart(user.id()));

        log.info("AddToCart -> cart:{}",cartEntity.toString());

        Optional<CartItemEntity> existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productInventoryContract.id(), cartEntity.getId());

        if (existingItem.isPresent()) {

            CartItemEntity cartItemEntity = existingItem.get();

            if(cartItemEntity.getQuantity() >= inventory.count()){
                log.error("AddToCart -> ProductNotEnoughAvailableQuantityException -> productId:{} , inventoryCount:{} , cartQuantity:{}",inventory.productId(),inventory.count(),cartItemEntity.getQuantity());
                throw new ProductNotEnoughAvailableQuantityException(inventory.productId().toString());
            }

            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);

        } else {
            log.info("AddToCart -> Cart created -> productId:{}",productInventoryContract.id());
            cartEntity.addItem(new CartItemEntity(cartEntity, productInventoryContract.id(), 1));
        }

        log.info("AddToCart -> SUCCESS");

        return OperationResult.SUCCESS;

    }

    @Override
    @Transactional
    public Cart getCart() {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(),List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE)).orElseThrow(() -> {
            log.error("GetCart -> CartNotFoundException -> No available cart with ACTIVE OR NEED_UPDATE status for userId:{} ",user.id());
            return new CartNotFoundException();
        });

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        boolean doCartNeedUpdate = cartEntity.getStatus().equals(CartStatus.NEED_UPDATE);

        List<ProductInventoryContract> productsInventory = getProductsInventoriesContractUseCase.getProductsInventories(listOfProductIds);

        if(!doCartNeedUpdate){

            Map<Long, Integer> inventoryMap = productsInventory.stream().collect(
                    Collectors.toMap(
                            ProductInventoryContract::id,
                            item -> item.inventory().count()
                    )
            );

            doCartNeedUpdate = cartEntity.getItems().stream().anyMatch(item -> {
                Integer availableCount = inventoryMap.get(item.getProductId());
                return availableCount != null && item.getQuantity() > availableCount;
            });

            log.info("GetCart -> Change cart status to NEED_UPDATE -> cartId:{} , user:{}",cartEntity.getId(),user.userName());

            if(doCartNeedUpdate){
                cartEntity.setStatus(CartStatus.NEED_UPDATE);
            }
        }

        log.info("GetCart -> userId:{}, cart:{}",user.userName(), cartEntity);

        return Cart.builder()
                .id(cartEntity.getId())
                .status(cartEntity.getStatus())
                .needUpdate(doCartNeedUpdate)
                .items(cartEntity.getItems().stream().map( item -> {

                    ProductInventoryContract productContract = productsInventory.stream().filter(it -> it.id().equals(item.getProductId())).findFirst().orElse(null);

                    return productContract != null ?
                            CartItem.builder()
                                .id(item.getId())
                                .product(ProductContract.from(productContract))
                                .quantity(item.getQuantity())
                                .build() : CartItem.of(item);

                }).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public OperationResult removeFromCart(Product product) {

        ProductContract productContract = getProductContractByIdUseCase.getProductContractById(product.id());

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartExists = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(),List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE)).orElseThrow(() -> {
            log.error("RemoveFromCart -> CartNotFoundException -> No available cart with ACTIVE OR NEED_UPDATE status for userId:{} ",user.id());
            return new CartNotFoundException();
        });

        CartItemEntity existingItem = cartItemJpaRepository.findByProductIdAndCart_Id(productContract.id(), cartExists.getId()).orElseThrow(() -> {
            log.error("RemoveFromCart -> ProductNotFoundInCartException -> product:{}",productContract);
            return new ProductNotFoundInCartException(productContract.id());
        });

        if (existingItem.getQuantity() > 1) {
            log.info("RemoveFromCart -> Decrease from user cart -> product:{},user:{},cart:{}",productContract.id(),user.userName(), cartExists);
            existingItem.setQuantity(existingItem.getQuantity() - 1);
        } else {
            log.info("RemoveFromCart -> Product removed from user cart -> product:{},user:{},cart:{}",productContract.id(),user.userName(), cartExists);
            cartItemJpaRepository.delete(existingItem);
        }

        log.info("RemoveFromCart -> SUCCESS");
        return OperationResult.SUCCESS;

    }

    @Override
    @Transactional
    public OperationResult updateCart(UUID cartId) {

        UserContract user = getCurrentUserUseCase.getCurrentUser();

        CartEntity cartEntity = cartJpaRepository.findByCustomerIdAndStatusIn(user.id(),List.of(CartStatus.ACTIVE,CartStatus.NEED_UPDATE)).orElseThrow(() -> {
            log.error("UpdateCart -> CartNotFoundException -> Cart not found -> cartId:{} , userId:{}",cartId,user.id());
            return new CartNotFoundException();
        });

        if(!cartEntity.getStatus().equals(CartStatus.NEED_UPDATE)){
            log.info("UpdateCart -> Cart is already up to date. -> cartId:{} , userId:{}",cartId,user.id());
            return OperationResult.SUCCESS;
        }

        List<Long> listOfProductIds = cartEntity.getItems().stream().map(CartItemEntity::getProductId).collect(Collectors.toList());

        List<ProductInventoryContract> productsInventory = getProductsInventoriesContractUseCase.getProductsInventories(listOfProductIds);

        Map<Long, Integer> inventoryMap = productsInventory.stream().collect(
                Collectors.toMap(ProductInventoryContract::id, item -> item.inventory().count())
        );

        List<CartItemEntity> cartItems = cartItemJpaRepository.findAllByCart(cartEntity);

        log.info("UpdateCart -> update cart items with inventory");

        cartItems.forEach(item -> {
            Integer count = inventoryMap.get(item.getProductId());

            if (count == null || count <= 0) {
                cartEntity.removeItem(item);
            } else if (count < item.getQuantity()) {
                item.setQuantity(count);
            }
        });

        cartEntity.setStatus(CartStatus.ACTIVE);
        log.info("UpdateCart -> SUCCESS -> Cart status changed to ACTIVE -> cartId:{} , userId:{}",cartId,user.userName());
        return OperationResult.SUCCESS;
    }

    @Override
    @Transactional
    public CartEntity createCart(UUID customerId) {
        log.info("CreateCart -> customerId:{}",customerId);
        return cartJpaRepository.save(new CartEntity(customerId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCartStatus(UUID cartId, CartStatus cartStatus) {
        log.info("UpdateCartStatus -> cartId:{}, cartStatus:{}",cartId,cartStatus);
        cartJpaRepository.updateCartStatus(cartId,cartStatus);
    }


}
