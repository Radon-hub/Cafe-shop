package com.radon.presentation.dto;

import com.radon.exception.types.IllegalStateException;

public record CategoryDeleteRequest(
        Long id,
        String name
) {
    public CategoryDeleteRequest {
        if (id == null) {
            throw new IllegalStateException("id cannot be null!");
        }
        if (name == null) {
            throw new IllegalStateException("name cannot be null!");
        }
    }
}
