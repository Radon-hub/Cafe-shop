package com.radon.domain;

import com.radon.infrastructure.entity.RoleEntity;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record Role(
        Long id,
        String role,
        Set<Authority> authorities
) {
    public static Role of(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .role(roleEntity.getRole())
                .authorities(roleEntity.getAuthorities().stream().map(Authority::of).collect(Collectors.toSet()))
                .build();
    }
}
