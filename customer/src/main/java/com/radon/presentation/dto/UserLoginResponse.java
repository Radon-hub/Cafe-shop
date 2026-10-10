package com.radon.presentation.dto;

public record UserLoginResponse(
        String accessToken,
        String refreshToken
) {
}
