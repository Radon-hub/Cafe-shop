package com.radon.application.port.in;

import com.radon.domain.JwtTokens;

public interface LoginUserUseCase {
    JwtTokens loginUser(String username, String password);
}
