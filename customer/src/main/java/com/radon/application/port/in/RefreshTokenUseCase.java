package com.radon.application.port.in;

import com.radon.domain.JwtTokens;

public interface RefreshTokenUseCase {
    JwtTokens refreshToken(String refreshToken);
}
