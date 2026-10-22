package com.radon.domain;

import com.radon.infrastructure.entity.UserEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record Cart(
        UUID id,
        User user,
        List<CartItem> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static User of(UserEntity userEntity){
    }
}
