package com.radon.domain;

import com.radon.infrastructure.entity.AuthorityEntity;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Builder
public record Authority(
        Long id,
        String authority
) implements GrantedAuthority {

    public static Authority of(AuthorityEntity entity) {
        return Authority.builder()
                .id(entity.getId())
                .authority(entity.getAuthority())
                .build();
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
