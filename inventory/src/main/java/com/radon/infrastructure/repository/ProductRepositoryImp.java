package com.radon.infrastructure.repository;

import com.radon.application.port.out.InventoryRepository;
import com.radon.application.port.out.ProductRepository;
import com.radon.application.port.out.WarehouseRepository;
import com.radon.domain.Inventory;
import com.radon.domain.Product;
import com.radon.domain.Warehouse;
import com.radon.exception.types.ProductExistsException;
import com.radon.exception.types.ProductNotFoundException;
import com.radon.infrastructure.entity.ProductEntity;
import com.radon.infrastructure.entity.WarehouseEntity;
import com.radon.infrastructure.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

    public ProductRepositoryImp(ProductJpaRepository productJpaRepository, WarehouseRepository warehouseRepository, InventoryRepository inventoryRepository) {
        this.productJpaRepository = productJpaRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Product getProductById(Long id) {

        ProductEntity productEntity = productJpaRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id.toString())
        );

        return Product.of(productEntity);

    }

    @Override
    public Long addNewProduct(Product product) {

        WarehouseEntity warehouse = warehouseRepository.findWarehouseById(product.inventory().id());

        Optional<ProductEntity> existed = productJpaRepository.isProductExists(product.name(),product.category().id(),product.price(),product.weight());

        if(existed.isPresent()){
            throw new ProductExistsException(existed.get().getId());
        }

        ProductEntity productEntity = productJpaRepository.save(ProductEntity.of(product));

        inventoryRepository.addInventory(Inventory.builder().wareHouse(Warehouse.of(warehouse)).productId(productEntity.getId()).build());
        
        return productEntity.getId();
    }


}
