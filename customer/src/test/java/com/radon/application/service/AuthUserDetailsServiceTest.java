package com.radon.application.service;

import com.radon.infrastructure.entity.AuthorityEntity;
import com.radon.infrastructure.entity.RoleEntity;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.infrastructure.jpa.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthUserDetailsServiceTest {

    @InjectMocks
    private AuthUserDetailsService authUserDetailsService;

    @Mock
    private UserJpaRepository userJpaRepository;


    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity(
                "alireza",
                "kharazan",
                "username",
                "123456",
                Set.of(new RoleEntity("USER", Set.of(new AuthorityEntity("product:read"))))
        );
    }

    @Test
    void loadUserByUsername() {
        when(userJpaRepository.findByUsername("username")).thenReturn(Optional.of(user));

        UserDetails userDetails = authUserDetailsService.loadUserByUsername("username");

        assertNotNull(userDetails);
        log.debug("[TEST] : UserDetails got from authDetailsService -> {}", userDetails);
    }



}