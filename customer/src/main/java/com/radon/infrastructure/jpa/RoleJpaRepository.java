package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRole(String role);

    Boolean existsByRole(String role);
}
