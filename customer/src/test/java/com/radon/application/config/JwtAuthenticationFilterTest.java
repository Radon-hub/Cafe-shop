package com.radon.application.config;

import com.radon.application.service.AuthUserDetailsService;
import com.radon.application.service.JwtUtil;
import com.radon.domain.Authority;
import com.radon.domain.Role;
import com.radon.domain.User;
import jakarta.servlet.FilterChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class JwtAuthenticationFilterTest {

    private JwtUtil jwtUtil;

    @Mock
    private AuthUserDetailsService authUserDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private UserDetails userDetails;
    private UserDetails userDetailsWrong;
    private Authority authority = Authority.builder().authority("product:read").build();
    private Role role = Role.builder().role("USER").authorities(Set.of(authority)).build();
    private String accessToken;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("simpleKeyIsJustARegularAnd#SecretKeyTh@tUseShouldSave",600,1000);

        jwtFilter = new JwtAuthenticationFilter(jwtUtil, authUserDetailsService);

        SecurityContextHolder.clearContext();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        userDetails = User.builder()
                .id(UUID.randomUUID())
                .userName("user1")
                .password("password1")
                .firstName("firstName1")
                .lastName("lastName1")
                .roles(Set.of(role))
                .build();

        userDetailsWrong = User.builder()
                .id(UUID.randomUUID())
                .userName("user2")
                .password("pass2255")
                .firstName("firstName2")
                .lastName("lastName2")
                .roles(Set.of(role))
                .build();

        accessToken = jwtUtil.generateAccessToken(userDetails);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldContinueWhenAuthorizationHeaderIsMissing() throws Exception {
        jwtFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(authUserDetailsService);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldContinueWhenAuthorizationHeaderExists() throws Exception {

        request.addHeader("Authorization", "Bearer " + accessToken);

        when(authUserDetailsService.loadUserByUsername(userDetails.getUsername())).thenReturn(userDetails);

        jwtFilter.doFilter(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);

        assertEquals(userDetails.getUsername(), authentication.getName());

        log.info("[TEST] : User passed the filter chain successfully with this credentials -> {}" ,  userDetails);

    }

    @Test
    void shouldNotAuthenticateWhenPasswordDoesNotMatch() throws Exception {

        String token = jwtUtil.generateAccessToken(userDetailsWrong);
        request.addHeader("Authorization", "Bearer " + token);

        when(authUserDetailsService.loadUserByUsername("user2"))
                .thenReturn(userDetails);

        jwtFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

}