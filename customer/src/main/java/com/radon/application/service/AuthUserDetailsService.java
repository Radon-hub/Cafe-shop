package com.radon.application.service;

import com.radon.domain.User;
import com.radon.exception.types.UserNotFoundException;
import com.radon.infrastructure.jpa.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public AuthUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userJpaRepository.findByUsername(username);
        return User.of(user.orElseThrow(() -> new UserNotFoundException(username)));
    }
}
