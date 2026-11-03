package com.radon.exception.types;

import com.radon.exception.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartNeedUpdateException extends ExceptionModel {
    public CartNeedUpdateException() {
        super("Your cart needs to be updated!");
    }
}
