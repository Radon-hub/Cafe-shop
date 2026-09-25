package com.radon.presentation.dto;

import com.radon.exception.types.IllegalStateException;

public record CategoryAddRequest(
        String name
) {
    public CategoryAddRequest {
        if (name == null) {
            throw new IllegalStateException("Name cannot be null!");
        }
    }
}
