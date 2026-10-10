package com.radon.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRegisterRequest(
        String firstName,
        String lastName,
        String userName,
        String password,
        List<Roles> roles
) {
}
