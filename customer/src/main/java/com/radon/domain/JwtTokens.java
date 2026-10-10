package com.radon.domain;

public record JwtTokens(
        String accessToken,
        String refreshToken
) {
}
