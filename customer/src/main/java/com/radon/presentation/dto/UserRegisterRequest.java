package com.radon.presentation.dto;

import lombok.Builder;

@Builder
public record UserRegisterRequest(
        String firstName,
        String lastName,
        String userName,
        String password
) {
}
