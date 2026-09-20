package com.radon.domain;

import com.radon.infrastructure.entity.CategoryEntity;
import lombok.Builder;

@Builder
public record Category(
        Long id,
        String name
){
    public static Category of(CategoryEntity categoryEntity) {
        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }
}
