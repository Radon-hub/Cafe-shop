package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProduct_Id(Long productId);

    Optional<InventoryEntity> findByIdAndProduct_Id(Long id, Long productId);

    @Query("SELECT inv,wr,pr FROM InventoryEntity inv " +
            "JOIN WarehouseEntity wr ON wr = inv.wareHouse " +
            "JOIN ProductEntity pr ON pr = inv.product " +
            "WHERE inv.product.id IN :productIds")
    List<InventoryEntity> getByProductIdList(List<Long> productIds);
}
