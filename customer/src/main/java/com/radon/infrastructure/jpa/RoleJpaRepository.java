package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity,Long> {
}
