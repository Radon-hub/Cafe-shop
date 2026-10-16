package com.radon.application.service;

import com.radon.domain.Authority;
import com.radon.domain.Role;
import com.radon.domain.TokenType;
import com.radon.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

@Slf4j
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;

    private Authority authority = Authority.builder().authority("product:read").build();
    private Role role = Role.builder().role("USER").authorities(Set.of(authority)).build();

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("simpleKeyIsJustARegularAnd#SecretKeyTh@tUseShouldSave",600,1000);
        userDetails = User.builder()
                .id(UUID.randomUUID())
                .userName("user1")
                .password("password1")
                .firstName("firstName1")
                .lastName("lastName1")
                .roles(Set.of(role))
                .build();
        accessToken = jwtUtil.generateAccessToken(userDetails);
        refreshToken = jwtUtil.generateRefreshToken(userDetails);
    }

    @Test
    void userDetails() {
        Assertions.assertNotNull(userDetails);
        log.info("[TEST] : sample userDerails -> {}" ,  userDetails);
    }


    @Test
    void generateAccessToken() {

        Assertions.assertNotNull(accessToken);

        Assertions.assertEquals(3, accessToken.split("\\.").length);

        log.info("[TEST] : Generated access token");

        log.info("[TEST] : accessToken -> {}" ,  accessToken);

    }

    @Test
    void generateRefreshToken() {

        Assertions.assertNotNull(refreshToken);

        Assertions.assertEquals(3, refreshToken.split("\\.").length);

        log.info("[TEST] : Generated refresh token");

        log.info("[TEST] : refreshToken -> {}" ,  refreshToken);

    }

    @Test
    void getTokenType() {

        Assertions.assertEquals(TokenType.ACCESS,jwtUtil.getTokenType(accessToken));

        Assertions.assertEquals(TokenType.REFRESH,jwtUtil.getTokenType(refreshToken));

        log.info("[TEST] : Token types verified.");

        log.info("[TEST] : accessToken = {} / refreshToken = {}" ,  TokenType.ACCESS, TokenType.REFRESH);

    }

    @Test
    void isTokenValid() {

        Assertions.assertTrue(jwtUtil.isTokenValid(accessToken, userDetails));
        Assertions.assertTrue(jwtUtil.isTokenValid(refreshToken, userDetails));

        log.info("[TEST] : Tokens are valid.");

    }

    @Test
    void extractUsername() {

        Assertions.assertEquals(userDetails.getUsername(), jwtUtil.extractUsername(accessToken));

        Assertions.assertEquals(userDetails.getUsername(), jwtUtil.extractUsername(refreshToken));

        log.info("[TEST] : Usernames in Tokens and userDetails are equal.");

    }
}