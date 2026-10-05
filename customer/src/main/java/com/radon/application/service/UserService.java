package com.radon.application.service;

import com.radon.application.port.in.RegisterUserUseCase;
import com.radon.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements RegisterUserUseCase {



    @Override
    public User registerUser(User user) {
        return null;
    }
}
