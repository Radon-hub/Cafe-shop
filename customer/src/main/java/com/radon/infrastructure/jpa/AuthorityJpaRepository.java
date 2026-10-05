package com.radon.infrastructure.jpa;

import com.radon.infrastructure.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AuthorityJpaRepository extends JpaRepository<AuthorityEntity,Long> {
    Set<AuthorityEntity> findAllByAuthorityIn(Set<String> authorities);
}
