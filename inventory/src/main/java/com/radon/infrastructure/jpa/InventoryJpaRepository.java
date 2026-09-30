package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProduct_Id(Long productId);
    Optional<InventoryEntity> findByIdAndProduct_Id(Long id, Long productId);
}
