package com.radon.domain;

import com.radon.infrastructure.entity.AuthorityEntity;
import lombok.Builder;

@Builder
public record Authority(
        Long id,
        String authority
) {
    public static Authority of(AuthorityEntity entity) {
        return Authority.builder()
                .id(entity.getId())
                .authority(entity.getAuthority())
                .build();
    }
}
