package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CartNotAvailableException extends ExceptionModel {
    public CartNotAvailableException() {
        super("Cart is currently not available or checked out!");
    }
}
