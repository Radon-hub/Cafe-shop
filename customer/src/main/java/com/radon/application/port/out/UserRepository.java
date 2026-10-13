package com.radon.application.port.out;

import com.radon.domain.JwtTokens;
import com.radon.domain.User;

public interface UserRepository {
    User registerUser(User user);
    JwtTokens loginUser(String username, String password);
    JwtTokens refreshToken(String refreshToken);
}
