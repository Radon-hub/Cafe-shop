package com.radon.domain;

import com.radon.infrastructure.entity.CategoryEntity;
import com.radon.presentation.dto.CategoryAddRequest;
import com.radon.presentation.dto.CategoryDeleteRequest;
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

    public static Category of(CategoryAddRequest categoryAddRequest) {
        return Category.builder()
                .name(categoryAddRequest.name())
                .build();
    }
    public static Category of(CategoryDeleteRequest categoryDeleteRequest) {
        return Category.builder()
                .id(categoryDeleteRequest.id())
                .name(categoryDeleteRequest.name())
                .build();
    }
}
