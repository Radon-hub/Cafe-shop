package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {
}
