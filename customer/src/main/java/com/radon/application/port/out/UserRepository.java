package com.radon.application.port.out;

import com.radon.domain.User;

public interface UserRepository {
    User registerUser(User user);
    User getUserByUsername(String username);
}
