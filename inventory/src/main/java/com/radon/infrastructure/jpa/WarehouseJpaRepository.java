package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseEntity, Long> {
    Optional<WarehouseEntity> findByWarehouse(String warehouse);
}
