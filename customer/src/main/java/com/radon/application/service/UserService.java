package com.radon.application.service;

import com.radon.application.port.in.GetCurrentUserUseCase;
import com.radon.application.port.in.LoginUserUseCase;
import com.radon.application.port.in.RefreshTokenUseCase;
import com.radon.application.port.in.RegisterUserUseCase;
import com.radon.application.port.out.UserRepository;
import com.radon.domain.JwtTokens;
import com.radon.domain.User;
import com.radon.exception.types.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService implements RegisterUserUseCase, LoginUserUseCase, RefreshTokenUseCase, GetCurrentUserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        return userRepository.registerUser(user);
    }

    @Override
    public JwtTokens loginUser(String username, String password) {
        return userRepository.loginUser(username, password);
    }

    @Override
    public JwtTokens refreshToken(String refreshToken) {
        return userRepository.refreshToken(refreshToken);
    }

    @Override
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return (User) userDetails;
        }

        throw new UnAuthorizedException();

    }
}
