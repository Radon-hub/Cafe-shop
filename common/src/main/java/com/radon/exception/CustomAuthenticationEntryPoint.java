package com.radon.exception;

import com.radon.response.ErrorResponse;
import com.radon.response.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        mapper.writeValue(
                response.getOutputStream(),
                new Response<>(
                        null,
                        new ErrorResponse(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                "Unauthorized",
                                "Authentication required (Your user may not be authenticated or Token expired!)",
                                request.getRequestURI()
                        )
                )
        );

    }
}
