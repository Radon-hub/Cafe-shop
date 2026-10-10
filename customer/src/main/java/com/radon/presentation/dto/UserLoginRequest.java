package com.radon.presentation.dto;

public record UserLoginRequest(
        String userName,
        String password
) {
}
