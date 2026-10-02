package com.radon.infrastructure.repository;

import com.radon.application.port.out.CategoryRepository;
import com.radon.application.port.out.InventoryRepository;
import com.radon.application.port.out.ProductRepository;
import com.radon.application.port.out.WarehouseRepository;
import com.radon.domain.Category;
import com.radon.domain.Inventory;
import com.radon.domain.Product;
import com.radon.domain.Warehouse;
import com.radon.exception.types.CategoryNotFoundException;
import com.radon.exception.types.ProductExistsException;
import com.radon.exception.types.ProductNotFoundException;
import com.radon.infrastructure.entity.CategoryEntity;
import com.radon.infrastructure.entity.InventoryEntity;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.entity.WarehouseEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    public ProductRepositoryImp(ProductJpaRepository productJpaRepository, InventoryRepository inventoryRepository, CategoryRepository categoryRepository) {
        this.productJpaRepository = productJpaRepository;
        this.inventoryRepository = inventoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) {

        ProductEntity productEntity = productJpaRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id.toString())
        );

        return Product.of(productEntity);

    }

    @Override
    @Transactional
    public Product addNewProduct(Product product) {


        Optional<ProductEntity> existed = productJpaRepository.isProductExists(product.name(),product.category().id(),product.price(),product.weight());

        if(existed.isPresent()){
            throw new ProductExistsException(existed.get().getId());
        }

        CategoryEntity category = categoryRepository.findCategoryById(product.category().id());

        if(category != null){
            throw new CategoryNotFoundException(product.id());
        }

        ProductEntity productEntity = productJpaRepository.save(new ProductEntity(
                product.name(),
                product.description(),
                category,
                product.price(),
                product.weight()
        ));

        InventoryEntity inventory = inventoryRepository.addInventory(productEntity,Inventory.builder().wareHouse(Warehouse.builder().id(product.inventory().wareHouse().id()).build()).productId(productEntity.getId()).build());

        productEntity.setInventory(inventory);

        return Product.builder()
                .id(productEntity.getId())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .weight(product.weight())
                .category(Category.of(category))
                .inventory(Inventory.builder()
                        .id(inventory.getId())
                        .count(inventory.getCount())
                        .wareHouse(Warehouse.builder().id(inventory.getWareHouse().getId()).warehouse(inventory.getWareHouse().getWarehouse()).build())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {

        ProductEntity productEntity = productJpaRepository.findById(product.id()).orElseThrow(
                () -> new ProductNotFoundException(product.id().toString())
        );

        productEntity.updateName(product.name());
        productEntity.updateDescription(product.description());
        productEntity.updatePrice(product.price());
        productEntity.updateWeight(product.weight());

        if(product.category() != null && product.category().id() != null && product.category().id() > 0){

            CategoryEntity category = categoryRepository.findCategoryById(product.category().id());

            if(category == null){
                throw new CategoryNotFoundException(product.id());
            }

            productEntity.setCategory(category);

        }

        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .weight(productEntity.getWeight())
                .inventory(Inventory.builder().id(productEntity.getInventory().getId()).productId(product.id()).count(productEntity.getInventory().getCount())
                        .wareHouse(Warehouse.builder().warehouse(productEntity.getInventory().getWareHouse().getWarehouse()).id(productEntity.getInventory().getWareHouse().getId()).build())
                        .build())
                .category(Category.builder()
                        .id(productEntity.getCategory().getId())
                        .name(productEntity.getCategory().getName())
                        .build())
                .build();
    }


}
