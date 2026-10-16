package com.radon.application.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PasswordConfigTest {

    private PasswordEncoder passwordEncoder;

    private String password;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        password = "Password@1234";
        encodedPassword = passwordEncoder.encode(password);
    }

    @Test
    void passwordEncoder() {
        log.info("[TEST] : password -> {} | encoded -> {}" ,  password ,  encodedPassword);
        Assertions.assertNotNull(encodedPassword);
        Assertions.assertTrue(passwordEncoder.matches(password,encodedPassword));
    }
}