package com.radon.exception;

import com.radon.response.ErrorResponse;
import com.radon.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(ExceptionModel.class)
    public ResponseEntity<Response<Void>> handleException(ExceptionModel model) {

        ResponseStatus responseStatus =
                model.getClass().getAnnotation(ResponseStatus.class);

        HttpStatus status = responseStatus.value();

        return ResponseEntity.status(status).body(
                new Response<>(
                        null,
                        new ErrorResponse(
                                status.value(),
                                status.name(),
                                model.getMessage(),
                                request.getRequestURI()
                        )
                )
        );
    }

}
