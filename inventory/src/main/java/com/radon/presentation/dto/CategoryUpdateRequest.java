package com.radon.presentation.dto;

import com.radon.exception.types.IllegalStateException;

public record CategoryUpdateRequest(
        String oldName,
        String newName
) {
    public CategoryUpdateRequest {
        if (oldName == null || newName == null) {
            throw new IllegalStateException("oldName and newName cannot be null!");
        }
        if (oldName.equals(newName)) {
            throw new IllegalStateException("newName cannot be the same as oldName!");
        }
    }
}
