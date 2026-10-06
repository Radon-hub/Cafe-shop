package com.radon.application.port.in;

import com.radon.domain.User;

public interface GetUserByUsernameUseCase {
    User getUserByUsername(String username);
}
