package com.radon.exception;

import com.radon.response.ErrorResponse;
import com.radon.response.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public AuthAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getOutputStream(),
                new Response<>(
                        null,
                        new ErrorResponse(
                                403,
                                "FORBIDDEN",
                                "You do not have permission to access this resource",
                                request.getRequestURI()
                        )
                )
        );
    }
}
